package com.xyy.simplehomework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.adapter.SmallProjectAdapter;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.SmallProjectTitle;
import com.xyy.simplehomework.entity.Week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    private List<MultiItemEntity> data;
    private SmallProjectAdapter adapter;

    public WeekFragment() {
        data = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        weekRecyclerView.setAdapter(getAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        weekRecyclerView.setLayoutManager(layoutManager);
        adapter.expandAll();
        super.onViewCreated(view, savedInstanceState);
    }

    private SmallProjectAdapter getAdapter() {
        adapter = new SmallProjectAdapter(data);
        return adapter;
    }

    public void updateWeekList(Week week) {
        data = new ArrayList<>();

        List<SmallProjectTitle> title = (Arrays.asList(
                new SmallProjectTitle("已完成"),
                new SmallProjectTitle("未完成"),
                new SmallProjectTitle("待记录")
        ));

        for (MyProject project : week.projects) {
            title.get(project.status).addSubItem(project);
        }
        data.addAll(title);
        if (adapter != null) {
            adapter.replaceData(data);
            adapter.expandAll();
        }
    }
}
