package com.xyy.simplehomework.view.fragments.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Day;
import com.xyy.simplehomework.entity.Homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        HomeAdapter adapter = new HomeAdapter(R.layout.item_homework_tiny_recycler, Arrays.asList(new Day(new Date()), new Day(new Date())));
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    public static class HomeAdapter extends BaseQuickAdapter<Day, BaseViewHolder> {

        public HomeAdapter(int layoutResId, @Nullable List<Day> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Day item) {
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            recyclerView.setAdapter(new HomeworkAdapter(R.layout.item_homework_tiny, Arrays.asList(new Homework(), new Homework())));
        }
    }

    public static class HomeworkAdapter extends BaseQuickAdapter<Homework, BaseViewHolder> {

        public HomeworkAdapter(int layoutResId, @Nullable List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Homework item) {

        }
    }
}
