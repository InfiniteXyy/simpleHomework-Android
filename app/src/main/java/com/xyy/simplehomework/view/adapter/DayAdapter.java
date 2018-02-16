package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.handler.HomeworkClickHandler;

import java.util.List;


public class DayAdapter extends BaseQuickAdapter<Homework, DayAdapter.HomeworkHolder> {
    private HomeworkClickHandler handler;

    public DayAdapter(int layoutResId, List<Homework> data) {
        super(layoutResId, data);
        handler = new HomeworkClickHandler();
    }

    @Override
    protected void convert(HomeworkHolder helper, Homework item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.homework, item);
        binding.setVariable(BR.clickHandler, handler);
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

    public static class HomeworkHolder extends BaseViewHolder {

        public HomeworkHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
