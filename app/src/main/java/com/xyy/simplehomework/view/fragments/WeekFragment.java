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

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
import com.xyy.simplehomework.view.adapter.WeekSection;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    private Context mContext;
    private View headerView;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.empty_view, container, false);
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);

        WeekAdapter adapter = new WeekAdapter(R.layout.fragment_week, R.layout.header_week, getData());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);
        adapter.addHeaderView(headerView);
        adapter.expandAll();
        super.onViewCreated(view, savedInstanceState);
    }


    public List<WeekSection> getData() {
        ProjectViewModel viewModel = ((MainActivity) mContext).viewModel;
        List<WeekSection> temp = new ArrayList<>();
        for (Homework homework : App.getInstance().getBoxStore().boxFor(Homework.class).getAll()) {
            temp.add(new WeekSection(homework));
        }
        return temp;
    }
}
