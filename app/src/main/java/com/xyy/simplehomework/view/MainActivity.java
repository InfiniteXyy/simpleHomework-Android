package com.xyy.simplehomework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.HomeFragment;
import com.xyy.simplehomework.view.fragments.day.DayFragment;
import com.xyy.simplehomework.view.fragments.semester.SemesterFragment;
import com.xyy.simplehomework.view.fragments.week.WeekFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public MainViewModel viewModel;
    private Fragment lastFragment = new Fragment();
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view model
        viewModel = new MainViewModel(this);

        // set up view
        setUpView();
    }

    private void setUpView() {
        // init drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.day);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (TabFragment fragment : TabFragment.values()) {
            transaction.add(R.id.mainFragment, fragment.getFragment())
                    .hide(fragment.getFragment());
        }
        transaction.commit();

        // init bottomBar
        final BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Fragment thisFragment = TabFragment.from(tabId).getFragment();
                getSupportFragmentManager().beginTransaction()
                        .hide(lastFragment)
                        .show(thisFragment)
                        .commit();
                lastFragment = thisFragment;
            }
        });
    }

    public void showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }

    private enum TabFragment {
        home(R.id.home, HomeFragment.class),
        day(R.id.day, DayFragment.class),
        week(R.id.week, WeekFragment.class),
        semester(R.id.month, SemesterFragment.class);

        private final int menuId;
        private final Class<? extends Fragment> clazz;
        private Fragment fragment;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.clazz = clazz;
            this.menuId = menuId;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId)
                    return fragment;
            }
            return home;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }

        @NonNull
        public Fragment getFragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }
    }
}

