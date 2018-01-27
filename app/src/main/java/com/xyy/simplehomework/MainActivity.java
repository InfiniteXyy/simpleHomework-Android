package com.xyy.simplehomework;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.cards.RecyclerViewManager;
import com.xyy.simplehomework.entity.MySubject_;
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

    public static String[] weeks = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};


    private DrawerLayout drawerLayout;

    private MaterialDialog chooseDialog;

    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    private Box<MyProject> projectBox;
    private Query<MyProject> projectQuery;

    private RecyclerViewManager recyclerViewManager;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    private final CardView[] weekStatusLayout = new CardView[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewManager = new RecyclerViewManager();


        weekStatusLayout[0] = findViewById(R.id.monday_status    );
        weekStatusLayout[1] = findViewById(R.id.tuesday_status   );
        weekStatusLayout[2] = findViewById(R.id.wednesday_status );
        weekStatusLayout[3] = findViewById(R.id.thursday_status  );
        weekStatusLayout[4] = findViewById(R.id.friday_status    );

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

    @Override
    protected void onResume() {
        recyclerViewManager.updateProjects(projectQuery.find());
        super.onResume();
    }

    private void setUpViews() {


        // 设置工具栏和侧边框
        final Toolbar toolbar = findViewById(R.id.toolbar);
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
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                }
                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        Date date = new Date();
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("M.d").format(date));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置 AppBar
        final LinearLayout status = findViewById(R.id.status);
        final DayNameSwitcher dayName = new DayNameSwitcher(this);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int newAlpha = 255 + verticalOffset;
                newAlpha = newAlpha < 0 ? 0 : newAlpha;
                dayName.setAlpha((float) newAlpha/255);
                status.setAlpha((float) newAlpha/255);
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
                                                .input("Homework name", "",false, new MaterialDialog.InputCallback() {
                                                    @Override
                                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                                        MyProject myProject = new MyProject(input.toString());
                                                        myProject.testDate = selectedDate;
                                                        myProject.subject.setTarget(selectedSubject);
                                                        projectBox.put(myProject);
                                                        updateStatus();

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
        final ViewPager viewPager = findViewById(R.id.MyViewPager);

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



        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            weekStatusLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI, true);

                    for (CardView card : weekStatusLayout) {
                        if (card != weekStatusLayout[finalI]) {
                            card.setAlpha(0.5f);
                        }
                    }
                }
            });
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                weekStatusLayout[position].setAlpha(1-positionOffset/2);
                if (position < 4)
                    weekStatusLayout[position+1].setAlpha(positionOffset/2 + 0.5f);
            }
            @Override
            public void onPageSelected(int position) {
                dayName.setText(position);
                recyclerViewManager.updateProjects(projectQuery.find());
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });


    }

    public void updateStatus() {
        for (int i = 0; i < 5; i++) {
            weekStatusLayout[i].getLayoutParams().height = recyclerViewManager.getDailyNum(i)*28+15;
        }

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
                chooseDialog.show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    public void finishProject(MyProject myProject) {
        projectBox.remove(myProject);
    }

    public void projectsDetail(MyProject project) {
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
        updateStatus();
    }
    public static int px2dip(int pxValue)
    {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue)
    {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }
}

