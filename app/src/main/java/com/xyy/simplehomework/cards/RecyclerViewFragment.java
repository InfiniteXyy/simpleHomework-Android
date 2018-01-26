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
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;

import java.util.List;

/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewFragment extends Fragment{
    RecyclerView recyclerView;
    public ProjectAdapter adapter;
    private TextView empty_layout;
    public RecyclerViewFragment(){
        adapter = new ProjectAdapter();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        empty_layout = view.findViewById(R.id.empty_view);
    }

    public void updateDailyProjects(List<MyProject> projectList) {
        adapter.setMyProjects(projectList);
        if (recyclerView == null) return;
        if (projectList.isEmpty()) {
            empty_layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            empty_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
