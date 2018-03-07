package com.xyy.simplehomework.view.helper;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xyy.simplehomework.R;


public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private float scale;
    private int headerHeight;
    private Drawable mDivider;     //分割线Drawable  
    private int mDividerHeight;  //分割线高度  

    /**
     * 使用line_divider中定义好的颜色
     *
     * @param context
     * @param dividerHeight 分割线高度
     */
    public SimpleDividerItemDecoration(Context context, int dividerHeight) {
        scale = context.getResources().getDisplayMetrics().density;
        headerHeight = dp2px(50);
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        mDividerHeight = dividerHeight;
    }

    /**
     * @param context
     * @param divider       分割线Drawable
     * @param dividerHeight 分割线高度
     */
    public SimpleDividerItemDecoration(Context context, Drawable divider, int dividerHeight) {
        scale = context.getResources().getDisplayMetrics().density;
        headerHeight = dp2px(50);
        if (divider == null) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        } else {
            mDivider = divider;
        }
        mDividerHeight = dividerHeight;
    }

    //获取分割线尺寸  
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft()+ dp2px(72);
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child.getHeight() < headerHeight) continue; // 标题不用画分割线

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private int dp2px(float dpValue){
        return (int)(dpValue * scale+0.5f);
    }
}