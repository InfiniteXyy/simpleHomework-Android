package com.xyy.simplehomework;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.MySubject_;
import com.xyy.simplehomework.fragments.DayFragment;
import com.xyy.simplehomework.fragments.WeekFragment;
import com.xyy.simplehomework.utils.DayNameSwitcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

public class MainActivity extends AppCompatActivity {

    public static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private DrawerLayout drawerLayout;

    private MaterialDialog chooseDialog;

    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    private Box<MyProject> projectBox;
    private Query<MyProject> projectQuery;

    private DataSubscriptionList subscriptions = new DataSubscriptionList();
    private FragmentTransaction transaction;

    private DayNameSwitcher dayName;

    private WeekFragment weekFragment;
    private DayFragment dayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        subjectBox = boxStore.boxFor(MySubject.class);
        projectBox = boxStore.boxFor(MyProject.class);
        projectQuery = projectBox.query().build();
        projectQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<MyProject>>() {
                    @Override
                    public void onData(List<MyProject> projects) {
                        DayFragment.updateProjects(projects);
                    }
                });

        setUpViews();

    }

    @Override
    protected void onDestroy() {
        subscriptions.cancel();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        DayFragment.updateProjects(projectQuery.find());
        super.onResume();
    }

    private void setUpViews() {
        // 设置工具栏和侧边框
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 开启事务
                switch (item.getItemId()) {
                    case R.id.day:
                        if (!item.isChecked()) {
                            transaction.show(dayFragment).hide(weekFragment);
                        }
                        break;
                    case R.id.week:
                        if (!item.isChecked()) {
                            transaction.show(weekFragment).hide(dayFragment);
                        }
                        break;
                    case R.id.month:
                        break;
                    case R.id.year:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                }
                return true;
            }
        });
        navigationView.setCheckedItem(R.id.day);
        ActionBar actionBar = getSupportActionBar();
        Date date = new Date();
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("M.d").format(date));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置 AppBar
        dayName = new DayNameSwitcher(this);
        final LinearLayout status = findViewById(R.id.status);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int newAlpha = 255 + verticalOffset;
                newAlpha = newAlpha < 0 ? 0 : newAlpha;
                dayName.setAlpha((float) newAlpha / 255);
                status.setAlpha((float) newAlpha / 255);
            }
        });
        // 设置增加框监听

        // 语数英demo
        subjectQuery = subjectBox.query().build();
        addSubjectsAndProjectsForDemo();

        List<MySubject> subjects = subjectQuery.find();
        final ArrayList<String> subjectNames = new ArrayList<>();
        for (MySubject subject : subjects) {
            subjectNames.add(subject.name);
        }

        chooseDialog = new MaterialDialog.Builder(this)
                .title(R.string.choose_subject)
                .items(subjectNames)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        final MySubject selectedSubject = subjectBox.query().equal(MySubject_.name, subjectNames.get(which)).build().findFirst();
                        new MaterialDialog.Builder(MainActivity.this)
                                .title(R.string.choose_date)
                                .items(weeks)
                                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        final int selectedDate = which;
                                        new MaterialDialog.Builder(MainActivity.this)
                                                .title(R.string.decide_name)
                                                .input("Homework name", "", false, new MaterialDialog.InputCallback() {
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
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        dayFragment = new DayFragment();
        transaction.add(R.id.mainFragment, dayFragment);
        weekFragment = new WeekFragment();
        transaction.add(R.id.mainFragment, weekFragment);
        transaction.show(dayFragment).hide(weekFragment);
        transaction.commitAllowingStateLoss();
    }


//    public void updateStatus() {
//        for (int i = 0; i < 5; i++) {
//            weekStatusLayout[i].getLayoutParams().height = recyclerViewManager.getDailyNum(i) * 28 + 15;
//        }
//
//    }

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
            case R.id.add_projects:
                chooseDialog.show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    public void updateDayName(int position) {
        dayName.setText(position);
    }

    public void finishProject(MyProject myProject) {
        projectBox.remove(myProject);
    }

    public void showProjectsDetail(MyProject project) {
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(ProjectActivity.PROJECT_ID, projectBox.getId(project));
        intent.putExtra(ProjectActivity.SUBJECT_ID, project.subject.getTargetId());
        startActivity(intent);
    }

    private void addSubjectsAndProjectsForDemo() {
        subjectBox.removeAll();
        projectBox.removeAll();
        MySubject chinese = new MySubject("计算机系统", R.drawable.chinese_pic, R.color.japanBrown);
        MySubject english = new MySubject("高等数学", R.drawable.english_pic, R.color.japanBlue);
        MySubject math = new MySubject("线性代数", R.drawable.math_pic, R.color.japanPink);
        subjectBox.put(chinese);
        subjectBox.put(english);
        subjectBox.put(math);
        MyProject myProject = new MyProject("当代学生");
        MyProject myProject1 = new MyProject("春风");
        MyProject myProject2 = new MyProject("新课标");

        myProject.subject.setTarget(chinese);
        myProject1.subject.setTarget(english);
        myProject2.subject.setTarget(math);
        projectBox.put(myProject);
        projectBox.put(myProject1);
        projectBox.put(myProject2);
    }

    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
}

