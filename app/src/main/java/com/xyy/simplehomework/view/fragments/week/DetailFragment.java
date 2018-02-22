package com.xyy.simplehomework.view.fragments.week;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
import com.xyy.simplehomework.view.adapter.WeekHeaderAdapter;
import com.xyy.simplehomework.view.adapter.WeekSection;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/2/22.
 */

public class DetailFragment extends Fragment {
    private ProjectViewModel viewModel;
    private OnWeekFragmentChange mListener;
    private View headerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnWeekFragmentChange) {
            mListener = (OnWeekFragmentChange) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnWeekFragmentChange");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.header_week, container, false);
        return inflater.inflate(R.layout.fragment_week_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ProjectViewModel.getInstance();

        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        WeekAdapter adapter = new WeekAdapter(R.layout.item_homework_detail, R.layout.item_small_title, getData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);

        WeekHeaderAdapter headerAdapter = new WeekHeaderAdapter(R.layout.item_project, viewModel.getSubjectsThisWeek());
        RecyclerView headerRecycler = headerView.findViewById(R.id.recycler_view);
        LinearLayoutManager headerManager = new LinearLayoutManager(getContext());
        headerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        headerRecycler.setLayoutManager(headerManager);
        headerRecycler.setAdapter(headerAdapter);
        headerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mListener.startSubjectActivity(((MySubject) adapter.getItem(position)).id);
            }
        });
        headerView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeToSubject();
            }
        });

        adapter.addHeaderView(headerView);
    }

    public List<WeekSection> getData() {
        List<WeekSection> temp = new ArrayList<>();
        temp.add(new WeekSection(true, "仅显示未完成"));
        for (Homework homework : viewModel.getHomeworkThisWeek()) {
            temp.add(new WeekSection(homework));
        }
        return temp;
    }
}
