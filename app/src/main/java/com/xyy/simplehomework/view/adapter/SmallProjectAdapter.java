package com.xyy.simplehomework.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.SmallProjectTitle;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class SmallProjectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, ProjectAdapter.ProjectHolder> {

    public static final int TYPE_HEADER = 3;
    public static final int TYPE_PROJECT = 4;

    public SmallProjectAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEADER, R.layout.item_project_small_title);
        addItemType(TYPE_PROJECT, R.layout.item_project_small);
    }

    @Override
    protected void convert(final ProjectAdapter.ProjectHolder helper, MultiItemEntity item) {
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
            default:
                ViewDataBinding binding = helper.getBinding();
                binding.setVariable(BR.smallProject, item);
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
