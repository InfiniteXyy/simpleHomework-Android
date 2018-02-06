package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.viewmodel.ProjectClickHandler;

import java.util.List;


public class ProjectAdapter extends BaseQuickAdapter<MyProject, ProjectAdapter.ProjectHolder> {

    public ProjectAdapter(int layoutResId, List<MyProject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectHolder helper, MyProject item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.project, item);
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
