package com.xyy.simplehomework.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.pdf.PdfDocument;
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
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class DayFragment extends Fragment {
    private Context mContext;
    private ViewPager viewPager;


    public DayFragment() {
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
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
        final TabLayout tabLayout = ((Activity) mContext).findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, false);
        super.onViewCreated(view, savedInstanceState);
    }

    private static class DayPageAdapter extends FragmentPagerAdapter {
        public DayPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DateHelper.weekInCn[position];
        }
    }
}
