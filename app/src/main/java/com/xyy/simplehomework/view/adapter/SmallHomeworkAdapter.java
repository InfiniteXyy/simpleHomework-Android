package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.handler.HomeworkClickHandler;

import java.util.List;


public class SmallHomeworkAdapter extends BaseItemDraggableAdapter<Homework, SmallHomeworkAdapter.SmallHomeworkHolder> {
    private HomeworkClickHandler handler;
    public SmallHomeworkAdapter(int layoutResId, List<Homework> data) {
        super(layoutResId, data);
        handler = new HomeworkClickHandler();
    }

    @Override
    protected void convert(final SmallHomeworkHolder helper, Homework item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.homework, item);
        binding.setVariable(BR.clickHandler, handler);
        helper.getView(R.id.expand_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setGone(R.id.details, !helper.isShowDetail);
                helper.isShowDetail = !helper.isShowDetail;
            }
        });
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

    public static class SmallHomeworkHolder extends BaseViewHolder {
        public boolean isShowDetail;
        public SmallHomeworkHolder(View view) {
            super(view);
            isShowDetail = false;
        }
        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
