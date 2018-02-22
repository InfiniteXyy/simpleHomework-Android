package com.xyy.simplehomework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.HomeFragment;
import com.xyy.simplehomework.view.fragments.SemesterFragment;
import com.xyy.simplehomework.view.fragments.day.DayFragment;
import com.xyy.simplehomework.view.fragments.week.WeekFragment;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

public class MainActivity extends AppCompatActivity {
    public ProjectViewModel viewModel;
    MaterialMenuDrawable homeBtn;
    Fragment lastFragment;
    private HomeFragment homeFragment;
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private SemesterFragment semesterFragment;
    private DrawerLayout drawerLayout;
    private BottomBar bottomBar;
    private boolean homebtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view model
        viewModel = new ProjectViewModel(this);

        // set up view
        setDefaultFragment();
        setUpTools();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        dayFragment = new DayFragment();
        weekFragment = new WeekFragment();
        semesterFragment = new SemesterFragment();
        homeFragment = new HomeFragment();
        transaction.add(R.id.mainFragment, dayFragment);
        transaction.add(R.id.mainFragment, weekFragment);
        transaction.add(R.id.mainFragment, semesterFragment);
        transaction.add(R.id.mainFragment, homeFragment);
        transaction.hide(weekFragment).hide(semesterFragment).hide(dayFragment);
        lastFragment = homeFragment;
        transaction.commitAllowingStateLoss();
    }

    private void setUpTools() {
        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("简记作业");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        homeBtn = new MaterialMenuDrawable(this, getResources().getColor(android.R.color.secondary_text_light), MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(homeBtn);
        // init drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.day);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.set:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.hide(lastFragment);
                // TODO:做一个首页
                // TODO:做title的切换动画
                if (tabId == R.id.home) {
                    ft.show(homeFragment).commit();
                    lastFragment = homeFragment;
                } else if (tabId == R.id.day) {
                    ft.show(dayFragment).commit();
                    lastFragment = dayFragment;
                } else if (tabId == R.id.week) {
                    ft.show(weekFragment).commit();
                    lastFragment = weekFragment;
                } else if (tabId == R.id.month) {
                    ft.show(semesterFragment).commit();
                    lastFragment = semesterFragment;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (homebtnBack) {
                    weekFragment.onChangeBack();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
                break;
            case R.id.add_projects:
                break;
            default:
        }
        return true;
    }

    public void setHomeButton(boolean needBack) {
        homebtnBack = needBack;
        homeBtn.animateIconState(needBack ? MaterialMenuDrawable.IconState.ARROW : MaterialMenuDrawable.IconState.BURGER);
    }
}

