package com.xyy.simplehomework.view.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.xyy.simplehomework.entity.Homework;

/**
 * Created by xyy on 2018/2/4.
 */

public class WeekSection extends SectionEntity<Homework> {
    public WeekSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WeekSection(Homework homework) {
        super(homework);
    }
}
