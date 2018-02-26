package com.xyy.simplehomework.view.fragments.week;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class WeekAdapter extends BaseQuickAdapter<Homework, BaseDataBindingHolder> {
    private WeekHomeworkClick handler;

    public WeekAdapter(int layoutResId, @Nullable List<Homework> data, WeekHomeworkClick handler) {
        super(layoutResId, data);
        this.handler = handler;
    }

    @Override
    protected void convert(BaseDataBindingHolder helper, Homework item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.homework, item);
        binding.setVariable(BR.handler, handler);
        binding.executePendingBindings();
        GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
        circle.setColor(item.subject.getTarget().color);
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
