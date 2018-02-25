package com.xyy.simplehomework.view.fragments.week;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.List;

/**
 * Created by xyy on 2018/2/20.
 */

public class WeekHeaderAdapter extends BaseQuickAdapter<MySubject, BaseViewHolder> {
    public WeekHeaderAdapter(int layoutResId, @Nullable List<MySubject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySubject item) {
        GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
        circle.setColor(item.color);
        helper.setText(R.id.circle, item.getName().substring(0, 1));
    }
}
