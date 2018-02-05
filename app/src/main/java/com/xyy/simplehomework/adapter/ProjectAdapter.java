package com.xyy.simplehomework.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.handler.ProjectClickHandler;

import java.util.List;


public class ProjectAdapter extends BaseQuickAdapter<MyProject, ProjectAdapter.ProjectHolder> {

    private ProjectClickHandler handler;

    public ProjectAdapter(int layoutResId, List<MyProject> data) {
        super(layoutResId, data);
        handler = new ProjectClickHandler();
    }

    @Override
    protected void convert(ProjectHolder helper, MyProject item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.project, item);
        binding.setVariable(BR.projectClick, handler);
        ((CardView) helper.itemView)
                .setCardBackgroundColor(mContext.getResources().getColor(item.subject.getTarget().colorId));
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

    public static class ProjectHolder extends BaseViewHolder {

        public ProjectHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
