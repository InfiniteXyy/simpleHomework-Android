package com.xyy.simplehomework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xyy.simplehomework.cards.MyProject;
import com.xyy.simplehomework.cards.MySubject;
import com.xyy.simplehomework.cards.MySubject_;
import com.xyy.simplehomework.cards.ProjectAdapter;

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

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefresh;


    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    private Box<MyProject> projectBox;
    private Query<MyProject> projectQuery;


    private ProjectAdapter projectAdapter;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    private MaterialDialog chooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        subjectBox = boxStore.boxFor(MySubject.class);
        projectBox = boxStore.boxFor(MyProject.class);
        setUpViews();

        projectQuery = projectBox.query().build();
        projectQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<MyProject>>() {
                    @Override
                    public void onData(List<MyProject> projects) {
                        projectAdapter.setMyProjects(projects);
                    }
                });

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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);
        }

        // 设置列表
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        projectAdapter = new ProjectAdapter();
        recyclerView.setAdapter(projectAdapter);

        // 设置悬浮按钮监听
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title("title")
                        .content("this is my content")
                        .positiveText("agree")
                        .show();
            }
        });

        // 设置下拉刷新
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


//        MySubject chinese = new MySubject("语文", R.drawable.chinese_pic);
//        MySubject math = new MySubject("数学", R.drawable.math_pic);
//        MySubject english = new MySubject("英语", R.drawable.english_pic);

        // 设置增加框监听
//        subjectBox.removeAll();
//        projectBox.removeAll();
//        subjectBox.put(chinese);
//        subjectBox.put(math);
//        subjectBox.put(english);
        subjectQuery = subjectBox.query().build();
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
                        Log.d("projects count", "run: "+projectAdapter.getItemCount());
                        projectAdapter.notifyDataSetChanged();
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

