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
import com.xyy.simplehomework.cards.RecyclerViewManager;
import com.xyy.simplehomework.entity.MyProject;

import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class DayFragment extends Fragment {
    private RecyclerViewManager recyclerViewManager;
    private final CardView[] weekStatusLayout;
    ViewPager viewPager;

    public void updateProjects(List<MyProject> projects) {
        recyclerViewManager.updateProjects(projects);
    }

    public DayFragment() {
        recyclerViewManager = new RecyclerViewManager();
        weekStatusLayout = new CardView[5];

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        weekStatusLayout[0] = getActivity().findViewById(R.id.monday_status);
        weekStatusLayout[1] = getActivity().findViewById(R.id.tuesday_status);
        weekStatusLayout[2] = getActivity().findViewById(R.id.wednesday_status);
        weekStatusLayout[3] = getActivity().findViewById(R.id.thursday_status);
        weekStatusLayout[4] = getActivity().findViewById(R.id.friday_status);
        viewPager = view.findViewById(R.id.MyViewPager);
        recyclerViewManager = new RecyclerViewManager();
        setDailyViewPage();
    }

    private void setDailyViewPage() {
        // 设置 ViewPager
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 5;
            }
            @Override
            public Fragment getItem(int position) {
                return recyclerViewManager.recyclerViewFragments[position];
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return MainActivity.weeks[position];
            }
        });

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            weekStatusLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI, true);

                    for (CardView card : weekStatusLayout) {
                        if (card != weekStatusLayout[finalI]) {
                            card.setAlpha(0.5f);
                        }
                    }
                }
            });
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                weekStatusLayout[position].setAlpha(1 - positionOffset / 2);
                if (position < 4)
                    weekStatusLayout[position + 1].setAlpha(positionOffset / 2 + 0.5f);
            }

            @Override
            public void onPageSelected(int position) {
                ((MainActivity) getActivity()).updateDayName(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
