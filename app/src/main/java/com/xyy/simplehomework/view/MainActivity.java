package com.xyy.simplehomework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
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
    private Drawer drawer;
    private WeekFragment weekFragment;
    private DayFragment dayFragment;
    private SemesterFragment semesterFragment;

    public ProjectViewModel viewModel;

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

        // init drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.nav_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.day).withIcon(R.drawable.ic_today_black_24px),
                        new PrimaryDrawerItem().withName(R.string.week).withIcon(R.drawable.ic_view_week_black_24px),
                        new PrimaryDrawerItem().withName(R.string.month).withIcon(R.drawable.ic_assignment_black_24px),
                        new SectionDrawerItem().withName("详情"),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.drawable.ic_settings_black_24px),
                        new SecondaryDrawerItem().withName(R.string.year).withIcon(R.drawable.ic_account_circle_black_24px).withEnabled(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // get transaction helper
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        switch (position) {
                            case 1:
                                transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.DAY);
                                break;
                            case 2:
                                transaction.show(weekFragment).hide(dayFragment).hide(semesterFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.WEEK);
                                break;
                            case 3:
                                transaction.show(semesterFragment).hide(weekFragment).hide(dayFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.SEMESTER);
                                break;
                            case 5:
                                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                                break;
                            case 6:
                                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .build();
        drawer.setSelection(1);

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
                new DialogHelper(this).show();
                break;
            default:
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
