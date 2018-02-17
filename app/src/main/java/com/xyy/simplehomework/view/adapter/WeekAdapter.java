package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.handler.ProjectClickHandler;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class WeekAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, DayAdapter.HomeworkHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PROJECT = 1;
    public static final int TYPE_HOMEWORK = 2;

    private static ProjectClickHandler handler;

    public WeekAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEADER, R.layout.item_small_title);
        addItemType(TYPE_PROJECT, R.layout.item_project);
        addItemType(TYPE_HOMEWORK, R.layout.item_homework_detail);
    }

    @Override
    protected void convert(final DayAdapter.HomeworkHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_HEADER:
                final SmallTitle title = (SmallTitle) item;
                helper.setText(R.id.small_title, title.getTitle());
                break;
            case TYPE_HOMEWORK:
                final Homework homework = (Homework) item;
                if (handler == null) {
                    handler = new ProjectClickHandler(mContext);
                }
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.homework, item);
                binding.setVariable(BR.handler, handler);
                GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
                circle.setColor(homework.project.getTarget().subject.getTarget().color);

                break;
            case TYPE_PROJECT:
                final MyProject project = (MyProject) item;
                if (project.getStatus(false) == MyProject.NOT_ALL_FIN) {
                    helper.itemView.setVisibility(View.GONE);
                } else {
                    final MySubject subject = project.subject.getTarget();
                    circle = (GradientDrawable) helper.getView(R.id.circle_word).getBackground();
                    circle.setColor(Color.parseColor("#d1d3d4"));
                    helper.setText(R.id.circle_word, subject.getName().substring(0, 1));
                }
                break;
            default:
                break;
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
