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
import android.view.MotionEvent;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.home.HomeFragment;
import com.xyy.simplehomework.view.fragments.semester.SemesterFragment;
import com.xyy.simplehomework.view.fragments.week.WeekFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public MainViewModel viewModel;
    private Fragment lastFragment = new Fragment();
    private DrawerLayout drawerLayout;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view model
        viewModel = new MainViewModel(this);

        // set up view
        // init drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (TabFragment fragment : TabFragment.values()) {
            transaction.add(R.id.mainFragment, fragment.getFragment(), fragment.getTag())
                    .hide(fragment.getFragment());
        }

        transaction.commit();

        // init bottomBar
        bottomBar = findViewById(R.id.bottomBar);
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return ((HomeFragment)TabFragment.home.getFragment()).getDragHelper().onTouch(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (TabFragment fragment : TabFragment.values()) {
            if (!fragment.getFragment().isAdded())
                transaction.add(R.id.mainFragment, fragment.getFragment(), fragment.getTag())
                        .hide(fragment.getFragment());
        }
        transaction.show(lastFragment);
        transaction.commit();
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
        lastFragment = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (TabFragment tabFragment : TabFragment.values()) {
            transaction.remove(tabFragment.fragment);
        }
        transaction.commitAllowingStateLoss();
        super.onSaveInstanceState(outState);
    }

    private enum TabFragment {
        home(R.id.home, HomeFragment.class, "HomeFragment"),
        week(R.id.week, WeekFragment.class, "WeekFragment"),
        semester(R.id.month, SemesterFragment.class, "SemesterFragment");

        private final int menuId;
        private final Class<? extends Fragment> clazz;
        private final String tag;
        private Fragment fragment;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz, String tag) {
            this.clazz = clazz;
            this.menuId = menuId;
            this.tag = tag;
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


        public String getTag() {
            return tag;
        }
    }

}

