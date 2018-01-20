package com.xyy.simplehomework;

import android.app.DownloadManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefresh;


    private Box<MySubject> subjectBox;
    private Query<MySubject> subjectQuery;
    private SubjectAdapter adapter;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    final private int num_pics[] =  {
            R.drawable.n_0,
            R.drawable.n_1,
            R.drawable.n_2,
            R.drawable.n_3,
            R.drawable.n_4,
            R.drawable.n_5,
            R.drawable.n_6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        subjectBox = boxStore.boxFor(MySubject.class);
        subjectQuery = subjectBox.query().build();

        subjectQuery.subscribe().on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<MySubject>>() {
                    @Override
                    public void onData(List<MySubject> subjects) {
                        adapter.setMySubjects(subjects);
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
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SubjectAdapter();
        recyclerView.setAdapter(adapter);

        // 设置悬浮按钮监听
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "你点击了确认", Snackbar.LENGTH_INDEFINITE)
                        .setAction("了解~", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "什么都没有发生",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
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
                        adapter.notifyDataSetChanged();
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
                List<MySubject> mySubjects = adapter.getMySubjects();
                for (int i = 0; i < 7; i++) {
                    boolean exist = false;
                    for (MySubject subject : mySubjects) {
                        if (subject.getName().equals("num_"+i)) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        subjectBox.put(new MySubject("num_"+i, num_pics[i]));
                        return true;
                    }
                }
                Toast.makeText(this, "is full ", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}

