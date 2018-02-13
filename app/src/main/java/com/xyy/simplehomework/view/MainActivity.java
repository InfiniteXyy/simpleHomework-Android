package com.xyy.simplehomework.view;

import android.content.Intent;
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
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.DayFragment;
import com.xyy.simplehomework.view.fragments.SemesterFragment;
import com.xyy.simplehomework.view.fragments.WeekFragment;
import com.xyy.simplehomework.view.helper.DialogHelper;
import com.xyy.simplehomework.view.helper.TitleSwitcher;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public TitleSwitcher titleSwitcher;
    public ProjectViewModel viewModel;
    private WeekFragment weekFragment;
    private DayFragment dayFragment;
    private SemesterFragment semesterFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view
        setDefaultFragment();
        setUpTools();

        // set up view model
        viewModel = new ProjectViewModel(this);
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

    private void setUpTools() {
        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("M.d").format(new Date()));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // init AppBar
        titleSwitcher = new TitleSwitcher(this);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int newAlpha = 255 + verticalOffset;
                newAlpha = newAlpha < 0 ? 0 : newAlpha;
                titleSwitcher.setAlpha((float) newAlpha / 255);
            }
        });

        // init drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.day);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                switch (item.getItemId()) {
                    case R.id.day:
                        transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment).commit();
                        titleSwitcher.changeFragmentTitle(TitleSwitcher.DAY);
                        break;
                    case R.id.week:
                        transaction.show(weekFragment).hide(dayFragment).hide(semesterFragment).commit();
                        titleSwitcher.changeFragmentTitle(TitleSwitcher.WEEK);
                        break;
                    case R.id.month:
                        transaction.show(semesterFragment).hide(weekFragment).hide(dayFragment).commit();
                        titleSwitcher.changeFragmentTitle(TitleSwitcher.SEMESTER);
                        break;
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
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add_projects:
                new DialogHelper(this).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}

