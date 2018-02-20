package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.handler.ProjectClickHandler;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class WeekAdapter extends BaseSectionQuickAdapter<WeekSection, DayAdapter.HomeworkHolder> {
    private static ProjectClickHandler handler;

    public WeekAdapter(int layoutResId, int sectionHeadResId, List<WeekSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(DayAdapter.HomeworkHolder helper, WeekSection item) {
        if (handler == null) {
            handler = new ProjectClickHandler();
        }
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.homework, item.t);
        binding.setVariable(BR.handler, handler);
        GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
        circle.setColor(item.t.project.getTarget().subject.getTarget().color);
    }

    @Override
    protected void convertHead(DayAdapter.HomeworkHolder helper, WeekSection item) {

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
