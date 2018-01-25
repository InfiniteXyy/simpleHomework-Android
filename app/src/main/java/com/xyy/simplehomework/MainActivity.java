package com.xyy.simplehomework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xyy.simplehomework.cards.MyProject;
import com.xyy.simplehomework.cards.MySubject;
import com.xyy.simplehomework.cards.MySubject_;
import com.xyy.simplehomework.cards.ProjectAdapter;
import com.xyy.simplehomework.cards.RecyclerViewManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

public class MainActivity extends AppCompatActivity {

    String[] weeks = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefresh;

    private MaterialDialog chooseDialog;

    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    private Box<MyProject> projectBox;
    private Query<MyProject> projectQuery;

    private RecyclerViewManager recyclerViewManager;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewManager = new RecyclerViewManager();

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        subjectBox = boxStore.boxFor(MySubject.class);
        projectBox = boxStore.boxFor(MyProject.class);
        projectQuery = projectBox.query().build();
        projectQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<MyProject>>() {
                    @Override
                    public void onData(List<MyProject> projects) {
                        recyclerViewManager.updateProjects(projects);
                    }
                });
        setUpViews();
    }

    @Override
    protected void onDestroy() {
        subscriptions.cancel();
        super.onDestroy();
    }

    private void setUpViews() {
        // 设置工具栏和侧边框
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.day);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.day:  break;
                    case R.id.week: break;
                    case R.id.month:break;
                    case R.id.year:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        getApplicationContext().startActivity(intent);
                        break;
                }
                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("M.d").format(date));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置大字显示
        final TextView dayName = findViewById(R.id.day_name);
        dayName.setText(weeks[week_index].toUpperCase());
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int newAlpha = 255 + verticalOffset;
                newAlpha = newAlpha < 0 ? 0 : newAlpha;
                dayName.setAlpha((float) newAlpha/255);
            }
        });
//        // 设置悬浮按钮监听
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new MaterialDialog.Builder(MainActivity.this)
//                        .title("title")
//                        .content("this is my content")
//                        .positiveText("agree")
//                        .show();
//            }
//        });

        // 设置下拉刷新
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        // 设置增加框监听

        // 语数英demo
        subjectQuery = subjectBox.query().build();
        subjectBox.removeAll();
        projectBox.removeAll();
        MySubject chinese = new MySubject("语文", R.drawable.chinese_pic);
        MySubject english = new MySubject("英语", R.drawable.english_pic);
        subjectBox.put(chinese);
        subjectBox.put(english);
        subjectBox.put(new MySubject("数学", R.drawable.math_pic));
        MyProject myProject = new MyProject("当代学生");
        MyProject myProject1 = new MyProject("春风");
        MyProject myProject2 = new MyProject("新课标");
        MyProject myProject3 = new MyProject("sbbook");

        myProject.subject.setTarget(chinese);
        myProject1.subject.setTarget(english);
        myProject2.subject.setTarget(chinese);
        myProject3.subject.setTarget(chinese);
        projectBox.put(myProject);
        projectBox.put(myProject1);
        projectBox.put(myProject2);
        projectBox.put(myProject3);

        List<MySubject> subjects = subjectQuery.find();
        final ArrayList<String> subjectNames = new ArrayList<>();
        for (MySubject subject : subjects) {
            subjectNames.add(subject.name);
        }

        chooseDialog = new MaterialDialog.Builder(this)
                .title("Choose A Subject")
                .items(subjectNames)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        final MySubject selectedSubject = subjectBox.query().equal(MySubject_.name, subjectNames.get(which)).build().findFirst();
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("选择日期")
                                .items(weeks)
                                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        final int selectedDate = which;
                                        new MaterialDialog.Builder(MainActivity.this)
                                                .title("Decide the name")
                                                .input("Homework name", "",false, new MaterialDialog.InputCallback() {
                                                    @Override
                                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                                        MyProject myProject = new MyProject(input.toString());
                                                        myProject.testDate = selectedDate;
                                                        myProject.subject.setTarget(selectedSubject);
                                                        projectBox.put(myProject);
                                                    }
                                                })
                                                .build()
                                                .show();
                                        return false;
                                    }
                                })
                                .positiveText("choose")
                                .build()
                                .show();
                        return false;
                    }
                })
                .positiveText("choose")
                .build();

        // 设置 ViewPager
        ViewPager viewPager = findViewById(R.id.MyViewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() { return 5; }
            @Override
            public Fragment getItem(int position) {
                return recyclerViewManager.recyclerViewFragments.get(position);
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return weeks[position];
            }
        });
    }

    // 设置刷新监听
    private void refreshItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewManager.updateProjects(projectQuery.find());

                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    // 设置 toolbar 样式
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // 设置 toolbar 监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                chooseDialog.show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

