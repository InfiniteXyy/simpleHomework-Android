package com.xyy.simplehomework.view.fragments.home;


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
import com.xyy.simplehomework.view.App;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public final static String TAG = "HomeFragment";
    private FragmentMine fragmentMine;
    private FragmentSubjects fragmentSubjects;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = view.findViewById(R.id.mainFragment);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (fragmentMine == null)
                            fragmentMine = new FragmentMine();
                        return fragmentMine;
                    case 1:
                        return new FragmentMine();
                    case 2:
                        if (fragmentSubjects == null)
                            fragmentSubjects = new FragmentSubjects();
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
                switch (position) {
                    case 0:
                        return "我的";
                    case 1:
                        return "计划";
                    case 2:
                        return "课程";
                }
                return "错误";
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
