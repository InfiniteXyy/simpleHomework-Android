package com.xyy.simplehomework.view.fragments.day;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.helper.DateHelper;

/**
 * Created by xyy on 2018/1/27.
 */

public class DayFragment extends Fragment implements PageFragment.PageInteraction {
    private Context mContext;
    private ViewPager viewPager;
    private int dayOfWeek;
    private TabLayout tabs;
    private FloatingActionButton fab;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        //   Toolbar toolbar = view.findViewById(R.id.toolbar);
        viewPager = view.findViewById(R.id.view_page);
        tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setAdapter(new DayPageAdapter(getChildFragmentManager()));
        dayOfWeek = (DateHelper.getDayOfWeek() + 6) % 7;

        viewPager.setCurrentItem(dayOfWeek);

        fab = view.findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(dayOfWeek, true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == dayOfWeek) fab.hide();
                else if (position < dayOfWeek) {
                    fab.setImageResource(R.drawable.ic_arrow_forward_black_24px);
                    fab.show();
                } else {
                    fab.setImageResource(R.drawable.ic_arrow_back_black_24px);
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onScroll(boolean isUp) {
        if (!isUp) fab.show();
        else fab.hide();
    }


    private static class DayPageAdapter extends FragmentStatePagerAdapter {

        public DayPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance((position + 1) % 7);
        }

        //TODO:做一个拖动效果（如Trello）超难，最后再说吧
        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DateHelper.weekInCn[(position + 1) % 7];
        }


    }
}
