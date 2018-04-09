package com.xyy.simplehomework.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.MyFragment;
import com.xyy.simplehomework.view.fragments.home.HomeFragment;
import com.xyy.simplehomework.view.fragments.setting.SettingFragment;
import com.xyy.simplehomework.view.fragments.week.RecordFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.Arrays;
import java.util.List;

/**
 * subject main activity
 * contains {@link HomeFragment} {@link RecordFragment} {@link SettingFragment}
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyFragment lastFragment;
    private BottomBar bottomBar;
    private int themeId;
    private List<MyFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeId = getSharedPreferences("data", MODE_PRIVATE).getInt(SettingFragment.THEME, R.style.Theme1);
        setTheme(themeId);
        setContentView(R.layout.activity_main);

        fragments = Arrays.asList(
                new HomeFragment(),
                new RecordFragment(),
                new SettingFragment()
        );

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            transaction.add(R.id.mainFragment, fragment).hide(fragment);
        }
        transaction.show(fragments.get(0)).commit();
        lastFragment = fragments.get(0);

        // init bottomBar
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            int last = bottomBar.getCurrentTabPosition();
            int index = bottomBar.findPositionForTabWithId(tabId);
            MyFragment thisFragment = fragments.get(index);
            FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
            manager.setCustomAnimations(
                    last > index ? R.anim.slide_in_left : R.anim.slide_in_right,
                    last > index ? R.anim.slide_out_right : R.anim.slide_out_left)
                    .hide(lastFragment)
                    .show(thisFragment)
                    .commit();
            lastFragment = thisFragment;
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            lastFragment.onBackPressed();
    }

    public int getThemeId() {
        return themeId;
    }

    public void showAddDialog(MySubject subject) {
        bottomBar.selectTabAtPosition(1);

        ((RecordFragment) fragments.get(1)).showAddDialog(subject);
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

}

