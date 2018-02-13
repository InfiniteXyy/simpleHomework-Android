package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.handler.ProjectClickHandler;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class SmallProjectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, HomeworkAdapter.HomeworkHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PROJECT = 1;
    public static final int TYPE_HOMEWORK = 2;
    private ProjectClickHandler handler;

    public SmallProjectAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEADER, R.layout.item_project_small_title);
        addItemType(TYPE_PROJECT, R.layout.item_project_small);
        addItemType(TYPE_HOMEWORK, R.layout.item_homework_detail);
        handler = new ProjectClickHandler();
    }

    @Override
    protected void convert(final HomeworkAdapter.HomeworkHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_HEADER:
                final SmallProjectTitle title = (SmallProjectTitle) item;
                helper.setText(R.id.smallProjectTitle, title.getTitle())
                        .setImageResource(R.id.arrowForList, title.isExpanded() ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (title.isExpanded()) collapse(pos);
                        else expand(pos);
                    }
                });
                break;
            case TYPE_PROJECT:
                final MyProject project = (MyProject) item;
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.smallProject, item);
                binding.setVariable(BR.projectClick, handler);
                binding.setVariable(BR.subject, project.subject.getTarget());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (project.isExpanded()) collapse(pos);
                        else expand(pos);
                        Log.d("123", "onClick: " + project.subject.getTarget().name);
                    }
                });
                break;
            case TYPE_HOMEWORK:
                binding = helper.getBinding();
                binding.setVariable(BR.homework, item);
        }
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
