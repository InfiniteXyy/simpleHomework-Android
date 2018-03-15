package com.xyy.simplehomework.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.home.HomeFragment;
import com.xyy.simplehomework.view.fragments.semester.SemesterFragment;
import com.xyy.simplehomework.view.fragments.week.WeekFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    public MainViewModel viewModel;
    private Fragment lastFragment = new Fragment();
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = getSharedPreferences("data", MODE_PRIVATE).getInt("theme", R.style.Theme1);
        setTheme(style);

        setContentView(R.layout.activity_main);
        // set up view model
        viewModel = new MainViewModel(this);
        // set up view
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
                boolean fromRight = TabFragment.getPosition(lastFragment) < TabFragment.getPosition(thisFragment);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                fromRight ? R.anim.slide_in_right : R.anim.slide_in_left,
                                fromRight ? R.anim.slide_out_left : R.anim.slide_out_right)
                        .hide(lastFragment)
                        .show(thisFragment)
                        .commit();
                lastFragment = thisFragment;
            }
        });

        // init fab
        FloatingActionButton fab = findViewById(R.id.fab);
        ((WeekFragment) TabFragment.week.fragment).setFab(fab);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
        lastFragment = null;
    }

    @Override
    public void onBackPressed() {
        if (lastFragment.getChildFragmentManager().getBackStackEntryCount() == 0)
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                if (lastFragment instanceof HomeFragment)
                    finish();
                else bottomBar.selectTabAtPosition(0);
            else
                super.onBackPressed();
        else
            lastFragment.getChildFragmentManager().popBackStack();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        for (TabFragment tabFragment : TabFragment.values()) {
//            transaction.remove(tabFragment.fragment);
//        }
//        transaction.commitAllowingStateLoss();
//        super.onSaveInstanceState(outState);
//    }

    private enum TabFragment {
        home(R.id.home, HomeFragment.class, HomeFragment.TAG),
        week(R.id.week, WeekFragment.class, WeekFragment.TAG),
        semester(R.id.month, SemesterFragment.class, SemesterFragment.TAG);

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

        public static int getPosition(Fragment fragment) {
            if (fragment == null || fragment.getTag() == null)
                return 0;
            else {
                switch (fragment.getTag()) {
                    case HomeFragment.TAG:
                        return 0;
                    case WeekFragment.TAG:
                        return 1;
                    case SemesterFragment.TAG:
                        return 2;
                }
            }
            return 0;
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

