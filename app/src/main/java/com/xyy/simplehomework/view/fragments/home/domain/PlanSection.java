package com.xyy.simplehomework.view.fragments.home.domain;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.fragments.home.FragmentPlan;

/**
 * A decoration entity to wrap homework
 */

public class PlanSection extends AbstractExpandableItem<Homework> implements MultiItemEntity {
    private String sectionName;

    public PlanSection(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return FragmentPlan.TYPE_SECTION;
    }

}
