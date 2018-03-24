package com.xyy.simplehomework.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.home.HomeFragment;
import com.xyy.simplehomework.view.fragments.setting.SettingFragment;
import com.xyy.simplehomework.view.fragments.week.WeekFragment;
import com.xyy.simplehomework.view.fragments.week.WeeksFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public MainViewModel viewModel;
    private Fragment lastFragment;
    private BottomBar bottomBar;
    private int themeId;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeId = getSharedPreferences("data", MODE_PRIVATE).getInt(SettingFragment.THEME, R.style.Theme1);
        setTheme(themeId);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel(this);

        fragments = Arrays.asList(
                new HomeFragment(),
                new WeekFragment(),
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
            Fragment thisFragment = fragments.get(index);
            FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
            manager.setCustomAnimations(
                    last > index ? R.anim.slide_in_left : R.anim.slide_in_right,
                    last > index ? R.anim.slide_out_right : R.anim.slide_out_left)
                    .hide(lastFragment)
                    .show(thisFragment)

                    .commit();
            lastFragment = thisFragment;
            viewModel.popHomework();
        });

        // init fab
        FloatingActionButton fab = findViewById(R.id.fab);
        ((WeekFragment)fragments.get(1)).setFab(fab);

    }

    @Override
    protected void onDestroy() {
        viewModel.popHomework();
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
            lastFragment.getChildFragmentManager().popBackStack();
    }

    public int getThemeId() {
        return themeId;
    }

    public void showAddDialog(MySubject subject) {
        ((WeekFragment) fragments.get(1)).showAddDialog(subject);
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

