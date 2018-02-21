package com.xyy.simplehomework.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.SubjectActivity;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
import com.xyy.simplehomework.view.adapter.WeekHeaderAdapter;
import com.xyy.simplehomework.view.adapter.WeekSection;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    ProjectViewModel viewModel;
    private Context mContext;
    private View headerView;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        headerView = inflater.inflate(R.layout.header_week, container, false);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ProjectViewModel.getInstance();

        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        WeekAdapter adapter = new WeekAdapter(R.layout.item_homework_detail, R.layout.item_small_title, getData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);

        //TODO: 再研究一下这边的fragment调用
        WeekHeaderAdapter headerAdapter = new WeekHeaderAdapter(R.layout.item_project, viewModel.getSubjectsThisWeek());
        RecyclerView headerRecycler = headerView.findViewById(R.id.recycler_view);
        LinearLayoutManager headerManager = new LinearLayoutManager(mContext);
        headerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        headerRecycler.setLayoutManager(headerManager);
        headerRecycler.setAdapter(headerAdapter);
        headerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                MySubject subject = (MySubject) adapter.getItem(position);
                SubjectFragment fragment = SubjectFragment.newInstance(subject.id);
                transaction.add(R.id.mainFragment, fragment);
                transaction.hide(WeekFragment.this).show(fragment).addToBackStack(null).commit();
            }
        });
        // show all subjects
        headerView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubjectActivity.class);
                startActivity(intent);
            }
        });

        adapter.addHeaderView(headerView);
        super.onViewCreated(view, savedInstanceState);
    }


    public List<WeekSection> getData() {
        List<WeekSection> temp = new ArrayList<>();
        temp.add(new WeekSection(true, "仅显示未完成"));
        for (Homework homework : viewModel.getHomeworkThisWeek()) {
            temp.add(new WeekSection(homework));
        }
        return temp;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
