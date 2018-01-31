package com.xyy.simplehomework.cards;


import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewManager {
    final public static int WEEK_RANGE = 7;
    public RecyclerViewFragment[] recyclerViewFragments;
    private DateHelper dateHelper;

    public RecyclerViewManager(DateHelper dateHelper) {
        recyclerViewFragments = new RecyclerViewFragment[WEEK_RANGE];
        this.dateHelper = dateHelper;

        for (int i = 0; i < WEEK_RANGE; i++) {
            recyclerViewFragments[i] = new RecyclerViewFragment();
        }
    }

    public void updateProjects(List<MyProject> projects) {
        for (int i = 0; i < WEEK_RANGE; i++) {
            List<MyProject> temp = new ArrayList<>();
            for (MyProject project : projects) {
                if (dateHelper.getDayIndexOfWeek(project.deadline) == i) {
                    temp.add(project);
                }
            }
            recyclerViewFragments[i].updateDailyProjects(temp);
        }

    }
}
