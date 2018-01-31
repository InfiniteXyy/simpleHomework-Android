package com.xyy.simplehomework.cards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewFragment extends Fragment {
    private ProjectAdapter adapter;
    RecyclerView recyclerView;
    private List<MyProject> projectList;

    public RecyclerViewFragment() {
        projectList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new ProjectAdapter(R.layout.project_item, projectList);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        // 解除view父子关系（存在优化点，为什么呢）
//        View emptyView = adapter.getEmptyView();
//        if (emptyView != null && emptyView.getParent() != null)
//            ((ViewGroup) emptyView.getParent()).removeView(emptyView);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) view);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(false); // 每次移动都会显示动画
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((MainActivity) getActivity()).showProjectsDetail((MyProject) adapter.getData().get(position));
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((MainActivity) getActivity()).finishProject((MyProject) adapter.getData().get(position));
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateDailyProjects(List<MyProject> projectList) {
        this.projectList = projectList;
    }
}
