package com.xyy.simplehomework.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.adapter.SmallTitle;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    Context mContext;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        ProjectViewModel viewModel = ((MainActivity) mContext).viewModel;
        final List<MultiItemEntity> data = classifyList(viewModel.getProjectsThisWeek());
        WeekAdapter adapter = new WeekAdapter(data);
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (data.get(position) instanceof MyProject) {
                    if (((MyProject) data.get(position)).getStatus(false) == MyProject.NOT_ALL_FIN)
                        return 0;
                    else return 1;
                } else return 5;
            }
        });
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);
        adapter.expandAll();
        super.onViewCreated(view, savedInstanceState);
    }


    public List<MultiItemEntity> classifyList(List<MyProject> projects) {
        List<MultiItemEntity> data = new ArrayList<>();
        SmallTitle allFin = new SmallTitle("已完成");
        SmallTitle notAllFin = new SmallTitle("未完成");
        SmallTitle notRecord = new SmallTitle("待记录");

        for (MyProject project : projects) {
            switch (project.getStatus(true)) {
                case MyProject.ALL_FIN:
                    allFin.addSubItem(project);
                    break;
                case MyProject.NOT_ALL_FIN:
                    notAllFin.addSubItem(project);
                    break;
                default:
                    notRecord.addSubItem(project);
                    break;
            }
            project.setSubItems(project.homework);
        }
        data.add(notAllFin);
        data.add(notRecord);
        data.add(allFin);
        return data;
    }
}
