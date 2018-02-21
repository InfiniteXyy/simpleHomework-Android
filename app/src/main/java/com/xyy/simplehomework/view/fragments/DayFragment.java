package com.xyy.simplehomework.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.helper.DateHelper;

/**
 * Created by xyy on 2018/1/27.
 */

public class DayFragment extends Fragment {
    private Context mContext;
    private ViewPager viewPager;
    private TabLayout tabs;
    private String TAG = "123";

    public DayFragment() {

    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        tabs = ((Activity) context).findViewById(R.id.tabs);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.view_page);
        viewPager.setAdapter(new DayPageAdapter(getFragmentManager()));
        // init tabs
        tabs.setupWithViewPager(viewPager);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        tabs.setVisibility(hidden ? View.GONE : View.VISIBLE);
    }


    // TODO:不占据完整个页面
    private static class DayPageAdapter extends FragmentPagerAdapter {
        private int today;

        public DayPageAdapter(FragmentManager fm) {
            super(fm);
            today = DateHelper.getDayOfWeek();
        }

        //TODO:自动找到当前的日期，并始终将其设置为第一个Page
        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance((today + position) % 7);
        }

        //TODO: 在不是今天的page设置floatingbutton
        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DateHelper.weekInCn[(today + position) % 7];
        }
    }
}
