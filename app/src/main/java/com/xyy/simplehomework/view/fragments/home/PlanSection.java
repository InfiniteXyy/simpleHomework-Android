package com.xyy.simplehomework.view.fragments.home;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.entity.Homework;

/**
 * Created by xyy on 2018/3/11.
 */

public class PlanSection extends AbstractExpandableItem<Homework> implements MultiItemEntity {
    private String sectionName;

    public PlanSection(String sectionName) {

        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return FragmentPlan.TYPE_SECTION;
    }

    public void removeSubItems() {
        if (mSubItems != null)
            mSubItems.clear();
    }
}
