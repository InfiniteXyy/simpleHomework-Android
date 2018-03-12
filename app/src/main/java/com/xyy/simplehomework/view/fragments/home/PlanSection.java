package com.xyy.simplehomework.view.fragments.home;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.xyy.simplehomework.entity.Homework;

/**
 * Created by xyy on 2018/3/11.
 */

public class PlanSection extends AbstractExpandableItem<Homework>{
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
}
