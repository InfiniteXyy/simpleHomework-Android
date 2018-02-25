package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
import com.xyy.simplehomework.view.adapter.WeekHeaderAdapter;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

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
        headerView = inflater.inflate(R.layout.item_small_title, container, false);
        return inflater.inflate(R.layout.fragment_week_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ProjectViewModel.getInstance();

        // set main recyclerView
        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        WeekAdapter adapter = new WeekAdapter(R.layout.item_homework_detail, viewModel.getHomeworkThisWeek());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mListener.openAddDialog();
            }
        });
        // set small subject view
        WeekHeaderAdapter subjectHeaderAdapter = new WeekHeaderAdapter(R.layout.item_project, viewModel.getSubjectsThisWeek());

        final RecyclerView headerRecycler = view.findViewById(R.id.subject_recycler_view);
        LinearLayoutManager headerManager = new LinearLayoutManager(getContext());
        headerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        headerRecycler.setLayoutManager(headerManager);
        headerRecycler.setAdapter(subjectHeaderAdapter);
        subjectHeaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mListener.startSubjectActivity(((MySubject) adapter.getItem(position)).id);
            }
        });
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeToSubject();
            }
        });

        // add spinner
        AppCompatSpinner spinner = headerView.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text, getResources().getStringArray(R.array.week_homework_show_type));
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        headerView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.openAddDialog();
            }
        });
        adapter.addHeaderView(headerView);

    }
}
