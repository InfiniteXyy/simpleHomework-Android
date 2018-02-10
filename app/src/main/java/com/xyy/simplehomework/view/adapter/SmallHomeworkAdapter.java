package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.handler.HomeworkClickHandler;

import java.util.List;


public class SmallHomeworkAdapter extends BaseItemDraggableAdapter<Homework, SmallHomeworkAdapter.SmallHomeworkHolder> {
    private static Animation fadeIn;
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
        // set button for showing detail
        helper.getView(R.id.expand_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load anim only once
                if (fadeIn == null) {
                    fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                }
                View detail = helper.getView(R.id.details);
                if (detail.getVisibility() == View.GONE) {
                    // maybe button should rotate here instead of changing resource
                    ((ImageView) v).setImageResource(R.drawable.ic_expand_less_black_24px);
                    detail.setVisibility(View.VISIBLE);
                    detail.startAnimation(fadeIn);
                } else {
                    ((ImageView) v).setImageResource(R.drawable.ic_expand_more_black_24px);
                    detail.setVisibility(View.GONE);
                }
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
        public SmallHomeworkHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
