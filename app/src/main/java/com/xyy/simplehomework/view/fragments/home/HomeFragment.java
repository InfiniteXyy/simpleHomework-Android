package com.xyy.simplehomework.view.fragments.home;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.helper.KitHelper;
import com.xyy.simplehomework.view.fragments.MyFragment;
import com.xyy.simplehomework.view.fragments.week.WeekViewModel;

/**
 * Main Fragment for home page
 */
public class HomeFragment extends MyFragment implements HomeUIInteraction {
    public final static String TAG = "HomeFragment";
    private static String[] pageNames = {
            "我的",
            "计划",
            "课程"
    };
    private HomeViewModel viewModel;
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
        fragmentPlan = FragmentPlan.newInstance(weekViewModel.getHomeworkData());
        fragmentMine = new FragmentMine();
        fragmentSubjects = FragmentSubjects.newInstance(viewModel.getSubjectList(), this);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return fragmentMine;
                    case 1:
                        return fragmentPlan;
                    default:
                        return fragmentSubjects;
                }
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

        view.findViewById(R.id.popMenu).setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());
            popupMenu.show();
        });

        viewModel.getSubjectLiveData().observe(this, subjects -> fragmentSubjects.setSubjectList(subjects));

        weekViewModel.getAllHomeworkLiveData().observe(this, homework -> {
            if (fragmentPlan != null) fragmentPlan.classifyPlanHomework(homework);
        });

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        KitHelper.setUpIndicatorWidth(tabLayout);
    }


    @Override
    public void putSubject(MySubject subject) {
        viewModel.putSubject(subject);
    }
}
