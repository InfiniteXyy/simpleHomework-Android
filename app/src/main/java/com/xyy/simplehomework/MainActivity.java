package com.xyy.simplehomework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.xyy.simplehomework.cards.MyProject;
import com.xyy.simplehomework.cards.MySubject;
import com.xyy.simplehomework.cards.MySubject_;
import com.xyy.simplehomework.cards.RecyclerViewFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        navigationView.setCheckedItem(R.id.one);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        Date date = new Date();
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("MM月dd日").format(date));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置 HollyViewPager
        final Random random = new Random(1);
        HollyViewPager hollyViewPager = findViewById(R.id.hollyViewPager);
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {
                return ((random.nextInt(10))%10)/11f;
            }
        });


        hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() { return 5; }
            @Override
            public Fragment getItem(int position) {
                return recyclerViewManager.recyclerViewFragments.get(position);
            }
        });

        Log.d("123", "margin: "+hollyViewPager.getViewPager().getPageMargin());
        Log.d("123", "currentItem: "+hollyViewPager.getViewPager().getCurrentItem());


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
//        subjectBox.removeAll();
//        projectBox.removeAll();
//        MySubject chinese = new MySubject("语文", R.drawable.chinese_pic);
//        subjectBox.put(chinese);
//        subjectBox.put(new MySubject("数学", R.drawable.math_pic));
//        subjectBox.put(new MySubject("英语", R.drawable.english_pic));
//        MyProject myProject = new MyProject();
//        myProject.book = "123";
        subjectQuery = subjectBox.query().build();
//        myProject.subject.setTarget(subjectQuery.findFirst());
//        projectBox.put(myProject);
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
                                .title("Decide the name")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT
                                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                                .input("Homework name", "",false, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        MyProject myProject = new MyProject();
                                        myProject.book = input.toString();
                                        myProject.subject.setTarget(selectedSubject);
                                        projectBox.put(myProject);
                                    }
                                })
                                .build()
                                .show();
                        return true;
                    }
                })
                .positiveText("choose")
                .build();

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
}

