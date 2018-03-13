package com.xyy.simplehomework.view.fragments.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.fragments.week.WeekViewModel;

import java.lang.reflect.Field;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public final static String TAG = "HomeFragment";
    private HomeViewModel viewModel;
    private static String[] pageNames = {
            "我的",
            "计划",
            "课程"
    };
    private FragmentMine fragmentMine;
    private FragmentSubjects fragmentSubjects;
    private FragmentPlan fragmentPlan;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final WeekViewModel weekViewModel = ViewModelProviders.of(this).get(WeekViewModel.class);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        ViewPager viewPager = view.findViewById(R.id.mainFragment);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        fragmentMine = new FragmentMine();
                        return fragmentMine;
                    case 1:
                        fragmentPlan = FragmentPlan.newInstance(weekViewModel.getHomeworkData());
                        return fragmentPlan;
                    case 2:
                        fragmentSubjects = FragmentSubjects.newInstance(viewModel.getSubjectList());
                        return fragmentSubjects;
                }
                return new FragmentMine();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageNames[position];
            }
        });

        weekViewModel.getHomeworkLiveData().observe(this, new Observer<List<Homework>>() {
            @Override
            public void onChanged(@Nullable List<Homework> homework) {
                if (fragmentPlan != null) {
                    fragmentPlan.classifyPlanHomework(homework);
                }
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpIndicatorWidth(tabLayout);
    }

    private void setUpIndicatorWidth(TabLayout tabLayout) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(App.dp2px(18f));
                    params.setMarginEnd(App.dp2px(18f));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
