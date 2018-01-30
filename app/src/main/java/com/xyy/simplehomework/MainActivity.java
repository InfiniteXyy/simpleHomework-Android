package com.xyy.simplehomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Semester_;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.fragments.DayFragment;
import com.xyy.simplehomework.fragments.SemesterFragment;
import com.xyy.simplehomework.fragments.WeekFragment;
import com.xyy.simplehomework.utils.AddProjectDialog;
import com.xyy.simplehomework.utils.DateHelper;
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

    static String TAG = "simpleHomework";
    public DateHelper dateHelper;
    private DrawerLayout drawerLayout;
    private AddProjectDialog addDialog;
    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    public Box<MyProject> projectBox;
    private Query<MyProject> projectQuery;
    private Box<Semester> semesterBox;
    private Week thisWeek;
    private Semester thisSemester;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();
    private DayNameSwitcher dayName;
    private WeekFragment weekFragment;
    private DayFragment dayFragment;
    private SemesterFragment semesterFragment;
    private LinearLayout status;

    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        setDefaultFragment();

        subjectBox = boxStore.boxFor(MySubject.class);
        projectBox = boxStore.boxFor(MyProject.class);
        semesterBox = boxStore.boxFor(Semester.class);
//        boxStore.boxFor(Week.class).removeAll();
//        semesterBox.removeAll();


        projectQuery = projectBox.query().build();
        subjectQuery = subjectBox.query().build();

        dateHelper = new DateHelper();
        thisWeek = getThisWeek();

        addSubjectsAndProjectsForDemo();
        for (MySubject subject : thisSemester.allSubjects) {
            Log.d(TAG, "onCreate: thisweek.weekIndex = " + thisWeek.weekIndex);
            for (byte aWeek : subject.availableWeeks)
                if (thisWeek.weekIndex == aWeek) {
                    Log.d(TAG, "onCreate: " + subject.name + "，" + aWeek);
                    thisWeek.subjects.add(subject);
                    break;
                }
        }

        weekFragment.updateWeekList(thisWeek.subjects, thisWeek.weekIndex);
        projectQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<MyProject>>() {
                    @Override
                    public void onData(List<MyProject> projects) {
                        dayFragment.updateProjects(projects);
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
        dayFragment.updateProjects(projectQuery.find());
        super.onResume();
    }

    private Week getThisWeek() {
        thisSemester = getThisSemester(dateHelper.date);
        dateHelper.setSemester(thisSemester);
        int weekIndex = dateHelper.getWeeksAfter(thisSemester.startDate);

        Log.d(TAG, "getThisWeek: " + weekIndex);
        // 有跨年bug
        for (Week week : thisSemester.weeks) {
            if (week.weekIndex == weekIndex) {
                return week;
            }
        }
        // 若没有设置过，则创建
        Log.d(TAG, "getThisWeek: set new week");
        Week week = new Week();
        week.weekIndex = weekIndex;
        week.semester.setTarget(thisSemester);
        return week;
    }

    private Semester getThisSemester(Date date) {
        Semester semester = semesterBox.query()
                .greater(Semester_.endDate, date)
                .less(Semester_.startDate, date)
                .build().findFirst();
        if (semester == null) {
            Log.d(TAG, "getThisSemester: semester is null");
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 0, 1);
            semester.endDate = new Date(118, 10, 2);
            semesterBox.put(semester);
        }
        return semester;
    }

    private void setUpViews() {
        // 设置工具栏和侧边框
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(navigationView);
                if (item.isChecked()) return true;
                // 开启事务
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                switch (item.getItemId()) {
                    case R.id.day:
                        transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment).commit();
                        status.setVisibility(View.VISIBLE);
                        dayName.changeFragmentTitle(DayNameSwitcher.DAY);
                        break;
                    case R.id.week:
                        transaction.show(weekFragment).hide(dayFragment).hide(semesterFragment).commit();
                        status.animate().alpha(0.0f).setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        status.setVisibility(View.GONE);
                                    }
                                });
                        dayName.changeFragmentTitle(DayNameSwitcher.WEEK);

                        break;
                    case R.id.month:
                        transaction.show(semesterFragment).hide(weekFragment).hide(dayFragment).commit();
                        status.animate().alpha(0.0f).setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        status.setVisibility(View.GONE);
                                    }
                                });
                        dayName.changeFragmentTitle(DayNameSwitcher.SEMESTER);

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
        status = findViewById(R.id.status);
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
        addDialog = new AddProjectDialog(this);
    }

    public List<String> getSubjectNames() {
        List<MySubject> subjects = subjectQuery.find();
        final ArrayList<String> subjectNames = new ArrayList<>();
        for (MySubject subject : subjects) {
            subjectNames.add(subject.name);
        }
        return subjectNames;
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        dayFragment = new DayFragment();
        transaction.add(R.id.mainFragment, dayFragment);
        weekFragment = new WeekFragment();
        transaction.add(R.id.mainFragment, weekFragment);
        semesterFragment = new SemesterFragment();

        transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment);
        transaction.commitAllowingStateLoss();
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
            case R.id.add_projects:
                addDialog.show();
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
        MySubject math2 = new MySubject("离散数学", R.drawable.math_pic, R.color.japanOrange);
        MySubject math3 = new MySubject("概率论", R.drawable.math_pic, R.color.japanTea);

        chinese.semester.setTarget(thisSemester);
        english.semester.setTarget(thisSemester);
        math.semester.setTarget(thisSemester);
        math2.semester.setTarget(thisSemester);
        math3.semester.setTarget(thisSemester);

        chinese.availableWeeks = new byte[]{4, 5, 6};
        english.availableWeeks = new byte[]{4, 5, 6};
        math.availableWeeks = new byte[]{4, 5, 6};
        math2.availableWeeks = new byte[]{4, 5};
        math3.availableWeeks = new byte[]{4};

        subjectBox.put(chinese);
        subjectBox.put(english);
        subjectBox.put(math);
        subjectBox.put(math2);
        subjectBox.put(math3);

        MyProject myProject = new MyProject("当代学生");
        MyProject myProject1 = new MyProject("春风");
        MyProject myProject2 = new MyProject("123");
        MyProject myProject3 = new MyProject("12");


        myProject.subject.setTarget(chinese);
        myProject1.subject.setTarget(english);
        myProject2.subject.setTarget(math);
        myProject3.subject.setTarget(math2);

        myProject.week.setTarget(thisWeek);
        myProject1.week.setTarget(thisWeek);
        myProject2.week.setTarget(thisWeek);
        myProject3.week.setTarget(thisWeek);

        projectBox.put(myProject);
        projectBox.put(myProject1);
        projectBox.put(myProject2);
        projectBox.put(myProject3);
    }
}

