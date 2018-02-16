package com.xyy.simplehomework.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        final List<MultiItemEntity> data = classifyList(viewModel.getAllProjects());
        WeekAdapter adapter = new WeekAdapter(data);
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position) instanceof MyProject ? 1 : 5;
            }
        });
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }


    public List<MultiItemEntity> classifyList(List<MyProject> projects) {
        List<MultiItemEntity> data = new ArrayList<>();
        data.add(new SmallTitle("未完成"));
        for (MyProject project : projects) {
            data.addAll(project.homework);
            Log.d("123", "useDemo: " + project.subject.getTarget().getName() + "~" + project.subject.getTarget());
        }
        data.add(new SmallTitle("待记录"));
        data.addAll(projects);
        data.add(new SmallTitle("已完成"));
        return data;
    }
}
