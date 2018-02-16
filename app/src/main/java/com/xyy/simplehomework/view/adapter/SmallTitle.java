package com.xyy.simplehomework.view.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by xyy on 2018/2/4.
 */

public class SmallTitle implements MultiItemEntity {
    private String title;

    public SmallTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getItemType() {
        return WeekAdapter.TYPE_HEADER;
    }
}
