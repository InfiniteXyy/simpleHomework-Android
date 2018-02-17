package com.xyy.simplehomework.view.adapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.entity.MyProject;

/**
 * Created by xyy on 2018/2/4.
 */

public class SmallTitle extends AbstractExpandableItem<MyProject> implements MultiItemEntity {
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

    @Override
    public int getLevel() {
        return 0;
    }
}
