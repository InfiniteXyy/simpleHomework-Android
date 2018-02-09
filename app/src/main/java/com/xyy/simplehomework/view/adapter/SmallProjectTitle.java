package com.xyy.simplehomework.view.adapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.entity.MyProject;

/**
 * Created by xyy on 2018/2/4.
 */

public class SmallProjectTitle extends AbstractExpandableItem<MyProject> implements MultiItemEntity {
    private String title;

    public SmallProjectTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (getSubItems() == null) return title + "(0)";
        return title + "(" + getSubItems().size() + ")";
    }

    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_HEADER;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
