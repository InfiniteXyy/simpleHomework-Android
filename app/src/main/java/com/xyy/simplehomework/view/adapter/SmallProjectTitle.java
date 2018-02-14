package com.xyy.simplehomework.view.adapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.entity.MyProject;

import java.util.List;

/**
 * Created by xyy on 2018/2/4.
 */

public class SmallProjectTitle extends AbstractExpandableItem<MyProject> implements MultiItemEntity {
    public boolean isExpandingAll;
    private String title;
    private int size = 0;

    public SmallProjectTitle(String title) {
        isExpandingAll = false;
        this.title = title;
    }

    public String getFormatTitle() {
        if (getSubItems() == null) return title + "(0)";
        return title + "(" + size + ")";
    }

    public int getItemSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void setSubItems(List<MyProject> list) {
        super.setSubItems(list);
        size = list.size();
    }

    @Override
    public void addSubItem(MyProject subItem) {
        super.addSubItem(subItem);
        size++;
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
