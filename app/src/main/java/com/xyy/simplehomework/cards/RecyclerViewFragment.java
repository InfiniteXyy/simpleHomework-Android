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

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;

import java.util.List;

/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewFragment extends Fragment {
    public ProjectAdapter adapter;
    RecyclerView recyclerView;

    public RecyclerViewFragment() {
        adapter = new ProjectAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateDailyProjects(List<MyProject> projectList) {
        adapter.setMyProjects(projectList);
    }
}
