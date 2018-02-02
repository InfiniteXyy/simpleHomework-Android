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

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.adapter.SmallProjectAdapter;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.SectionProject;
import com.xyy.simplehomework.entity.Week;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    private List<SectionProject> data;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(getAdapter());
        super.onViewCreated(view, savedInstanceState);
    }

    private SmallProjectAdapter getAdapter() {
        adapter = new SmallProjectAdapter(R.layout.item_project_small, R.layout.item_project_small_title, data);
        return adapter;
    }

    public void updateWeekList(Week week) {
        data = new ArrayList<>();
        int fin = 0;
        int tobe = 0;
        int record = 0;
        int size = week.projects.size();
        int i;
        for (i = 0; i < size; i++) {
            if (week.projects.get(i).status == MyProject.HAS_FINISHED) {
                data.add(new SectionProject(week.projects.get(i)));
                fin++;
            }
        }
        for (i = 0; i < size; i++) {
            if (week.projects.get(i).status == MyProject.TOBE_DONE) {
                data.add(new SectionProject(week.projects.get(i)));
                tobe++;
            }
        }
        for (i = 0; i < size; i++) {
            if (week.projects.get(i).status == MyProject.TOBE_RECORD) {
                data.add(new SectionProject(week.projects.get(i)));
                record++;
            }
        }
        data.add(fin + tobe, new SectionProject(true, "待记录(" + record + ")"));
        data.add(fin, new SectionProject(true, "未完成(" + tobe + ")"));
        data.add(0, new SectionProject(true, "已完成(" + fin + ")"));
        if (adapter != null) adapter.replaceData(data);
    }
}
