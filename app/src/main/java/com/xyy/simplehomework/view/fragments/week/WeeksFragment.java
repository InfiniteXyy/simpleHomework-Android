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
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/12.
 */

public class WeeksFragment extends Fragment {
    BaseQuickAdapter<Week, BaseViewHolder> adapter;
    BaseQuickAdapter<Week, BaseViewHolder> headerAdapter;
    private WeekUIInteraction mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof WeekUIInteraction) {
            mListener = (WeekUIInteraction) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement WeekUIInteraction");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        mListener.needBtns(hidden);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_weeks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Week> weeks = new ArrayList<>();
        List<Week> headerWeeks = new ArrayList<>();
        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            Week week = new Week(i);
            week.setHomeworkList(mListener.getViewModel().getHomeworkData(i));
            if (!week.hasFinished())
                weeks.add(week);
            else
                headerWeeks.add(week);
        }

        adapter = new BaseQuickAdapter<Week, BaseViewHolder>(R.layout.item_week_weeks, weeks) {
            @Override
            protected void convert(BaseViewHolder helper, Week item) {
                helper.addOnClickListener(R.id.layout);
                helper.setText(R.id.title, item.getWeekName());
                helper.setText(R.id.detail, item.getLatestDue());
                NumberProgressBar progressBar = helper.getView(R.id.progress);
                progressBar.setProgress(item.getProgress());
            }
        };

        adapter.setOnItemChildClickListener((adapter, view1, position) -> {
            Week week = (Week) adapter.getItem(position);
            mListener.onClickWeek(week.weekIndex, week.getHomeworkList());
        });
        recyclerView.setAdapter(adapter);

        RecyclerView headerRecycler = new RecyclerView(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        headerRecycler.setLayoutManager(linearLayoutManager);

        headerAdapter = new BaseQuickAdapter<Week, BaseViewHolder>(R.layout.item_week_small, headerWeeks) {
            @Override
            protected void convert(BaseViewHolder helper, Week item) {
                helper.setText(R.id.title, item.getWeekName());
            }
        };
        headerAdapter.setOnItemClickListener(((adapter1, view1, position) -> {
            Week week = (Week) adapter1.getItem(position);
            mListener.onClickWeek(week.weekIndex, week.getHomeworkList());
        }));
        headerRecycler.setAdapter(headerAdapter);
        adapter.addHeaderView(headerRecycler);
    }
}
