package com.xyy.simplehomework.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
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
    ViewPager viewPager;
    private final CardView[] weekStatusLayout = new CardView[5];
    private static RecyclerViewManager recyclerViewManager = new RecyclerViewManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.daily_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        weekStatusLayout[0] = view.findViewById(R.id.monday_status);
        weekStatusLayout[1] = view.findViewById(R.id.tuesday_status);
        weekStatusLayout[2] = view.findViewById(R.id.wednesday_status);
        weekStatusLayout[3] = view.findViewById(R.id.thursday_status);
        weekStatusLayout[4] = view.findViewById(R.id.friday_status);
        Log.d("123", "onViewCreated: " + weekStatusLayout[0]);
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
                return recyclerViewManager.recyclerViewFragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return MainActivity.weeks[position];
            }
        });

//        for (int i = 0; i < 5; i++) {
//            final int finalI = i;
//            weekStatusLayout[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    viewPager.setCurrentItem(finalI, true);
//
//                    for (CardView card : weekStatusLayout) {
//                        if (card != weekStatusLayout[finalI]) {
//                            card.setAlpha(0.5f);
//                        }
//                    }
//                }
//            });
//        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                weekStatusLayout[position].setAlpha(1 - positionOffset / 2);
//                if (position < 4)
//                    weekStatusLayout[position + 1].setAlpha(positionOffset / 2 + 0.5f);
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

    public static void updateProjects(List<MyProject> projects) {
        recyclerViewManager.updateProjects(projects);
    }
}
