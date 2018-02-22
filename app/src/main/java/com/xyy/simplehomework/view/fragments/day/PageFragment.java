package com.xyy.simplehomework.view.fragments.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.adapter.DayAdapter;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/2/17.
 */

public class PageFragment extends Fragment {
    public static final String DAY_ARGS = "day_arg";
    private int date;
    private DayAdapter adapter;
    private PageInteraction mListener;
    private List<Homework> data;

    public static PageFragment newInstance(int day) {
        Bundle args = new Bundle();
        args.putInt(DAY_ARGS, day);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getParentFragment() instanceof PageInteraction) {
            mListener = (PageInteraction) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement PageInteraction");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getInt(DAY_ARGS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_day, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        data = getData();
        adapter = new DayAdapter(R.layout.item_homework, data);
        adapter.setEmptyView(R.layout.empty_view, container);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (data.isEmpty()) return;
                if (dy < 0) {
                    mListener.onScroll(false);
                } else {
                    mListener.onScroll(true);
                }
            }
        });
        return view;
    }

    private List<Homework> getData() {
        return ProjectViewModel.getInstance().getHomework(this);
    }

    public Date getDate() {
        return DateHelper.afterDays(date);
    }

    public void updateAdapter(List<Homework> data) {
        adapter.replaceData(data);
    }

    public interface PageInteraction {
        void onScroll(boolean isUp);
    }
}
