package com.xyy.simplehomework.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.helper.DateHelper;
import com.xyy.simplehomework.helper.WeekPageHelper;

import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class DayFragment extends Fragment {
    private final CardView[] weekStatusLayout;
    private ViewPager viewPager;
    private WeekPageHelper weekPageHelper;
    MainActivity activity;

    public DayFragment() {
        weekStatusLayout = new CardView[WeekPageHelper.WEEK_RANGE];
        weekPageHelper = new WeekPageHelper(new DateHelper());
    }

    public void updateProjects(List<MyProject> projects) {
        weekPageHelper.updateProjects(projects);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        weekStatusLayout[0] = activity.findViewById(R.id.sunday_status);
        weekStatusLayout[1] = activity.findViewById(R.id.monday_status);
        weekStatusLayout[2] = activity.findViewById(R.id.tuesday_status);
        weekStatusLayout[3] = activity.findViewById(R.id.wednesday_status);
        weekStatusLayout[4] = activity.findViewById(R.id.thursday_status);
        weekStatusLayout[5] = activity.findViewById(R.id.friday_status);
        weekStatusLayout[6] = activity.findViewById(R.id.saturday_status);
        viewPager = view.findViewById(R.id.MyViewPager);

        setDailyViewPage();
    }

    private void setDailyViewPage() {
        // 设置 ViewPager
        viewPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return WeekPageHelper.WEEK_RANGE;
            }

            @Override
            public Fragment getItem(int position) {
                return weekPageHelper.recyclerViewFragments[position];
            }
        });
        viewPager.setCurrentItem(activity.dateHelper.getDayIndex());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                weekStatusLayout[position].setAlpha(1 - positionOffset / 2);
                if (position < WeekPageHelper.WEEK_RANGE - 1)
                    weekStatusLayout[position + 1].setAlpha(positionOffset / 2 + 0.5f);
            }

            @Override
            public void onPageSelected(int position) {
                activity.updateDayName(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void modifyViewPager(int position) {
        viewPager.setCurrentItem(position, true);
    }
}
