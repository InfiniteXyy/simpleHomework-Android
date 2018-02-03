package com.xyy.simplehomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.widget.LinearLayout;

import com.xyy.simplehomework.data.DataServer;
import com.xyy.simplehomework.fragments.DayFragment;
import com.xyy.simplehomework.fragments.SemesterFragment;
import com.xyy.simplehomework.fragments.WeekFragment;
import com.xyy.simplehomework.helper.DialogHelper;
import com.xyy.simplehomework.helper.TitleSwitcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity {
    public DataServer dataServer;

    private DrawerLayout drawerLayout;
    private DialogHelper addDialog;
    private LinearLayout status;

    private TitleSwitcher dayName;
    private WeekFragment weekFragment;
    private DayFragment dayFragment;
    private SemesterFragment semesterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up views
        setDefaultFragment();
        setUpTools();

        // set up dataServer
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        dataServer = new DataServer(boxStore);
        dataServer.useDemo();
        dataServer.bindToDateHelper();
    }

    @Override
    protected void onResume() {
        // bind views with data;
        dataServer.bindToViews(dayFragment, weekFragment, semesterFragment, dayName);
        super.onResume();
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
        dayName = new TitleSwitcher(this);
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

        // init drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(navigationView);
                if (item.isChecked()) return true;
                // get transaction helper
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                switch (item.getItemId()) {
                    case R.id.day:
                        transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment).commit();
                        status.setVisibility(View.VISIBLE);
                        dayName.changeFragmentTitle(TitleSwitcher.DAY);
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
                        dayName.changeFragmentTitle(TitleSwitcher.WEEK);
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
                        dayName.changeFragmentTitle(TitleSwitcher.SEMESTER);

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
        navigationView.setCheckedItem(R.id.day); // default choose

        // init dialog view
        addDialog = new DialogHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

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
}

