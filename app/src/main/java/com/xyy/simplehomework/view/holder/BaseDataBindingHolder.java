package com.xyy.simplehomework.view.holder;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;

/**
 * Created by xyy on 2018/2/25.
 */

public class BaseDataBindingHolder extends BaseViewHolder {
    public BaseDataBindingHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
