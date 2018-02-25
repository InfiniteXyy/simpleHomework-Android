package com.xyy.simplehomework.view.fragments.day;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;
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
    private List<Homework> data;

    public static PageFragment newInstance(int day) {
        Bundle args = new Bundle();
        args.putInt(DAY_ARGS, day);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
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

    public static class DayAdapter extends BaseQuickAdapter<Homework, BaseDataBindingHolder> {

        public DayAdapter(int layoutResId, List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, Homework item) {
            ViewDataBinding binding = helper.getBinding();
            binding.setVariable(BR.homework, item);
            binding.setVariable(BR.subject, item.subject.getTarget());
        }

        @Override
        protected View getItemView(int layoutResId, ViewGroup parent) {
            ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
            if (binding == null) {
                return super.getItemView(layoutResId, parent);
            }
            View view = binding.getRoot();
            view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
            return view;
        }
    }
}
