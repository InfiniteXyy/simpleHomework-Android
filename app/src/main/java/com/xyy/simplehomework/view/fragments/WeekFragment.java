package com.xyy.simplehomework.view.fragments;

import android.content.Context;
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
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.SmallProjectTitle;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.adapter.SmallProjectAdapter;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<MultiItemEntity> data = classifyList(viewModel.getAllProjects());
        SmallProjectAdapter adapter = new SmallProjectAdapter(data);
        weekRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        weekRecyclerView.setLayoutManager(layoutManager);

        // open all the level0 menu at the first time
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (SmallProjectAdapter.TYPE_HEADER == adapter.getItem(i).getItemType()) {
                adapter.expand(i);
            }
        }
        super.onViewCreated(view, savedInstanceState);
    }


    public List<MultiItemEntity> classifyList(List<MyProject> projects) {
        List<MultiItemEntity> data = new ArrayList<>();
        List<SmallProjectTitle> title = (Arrays.asList(
                new SmallProjectTitle("已完成"),
                new SmallProjectTitle("未完成"),
                new SmallProjectTitle("待记录")
        ));
        for (MyProject project : projects) {
            project.setSubItems(project.homework);
            if (project.homework.isEmpty()) {
                title.get(2).addSubItem(project);
            } else {
                title.get(1).addSubItem(project);
            }
        }
        data.addAll(title);
        return data;
    }
}
