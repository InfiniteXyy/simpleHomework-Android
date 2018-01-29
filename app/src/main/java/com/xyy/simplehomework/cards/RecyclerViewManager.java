package com.xyy.simplehomework.cards;


import com.xyy.simplehomework.entity.MyProject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewManager {
    final private static int WEEK_RANGE = 5;
    public RecyclerViewFragment[] recyclerViewFragments;

    public RecyclerViewManager() {
        recyclerViewFragments = new RecyclerViewFragment[WEEK_RANGE];
        for (int i = 0; i < WEEK_RANGE; i++) {
            recyclerViewFragments[i] = new RecyclerViewFragment();
        }
    }

    public void updateProjects(List<MyProject> projects) {
        for (int i = 0; i < WEEK_RANGE; i++) {
            List<MyProject> temp = new ArrayList<>();
            for (MyProject myProject : projects) {
                temp.add(myProject);
            }
            recyclerViewFragments[i].updateDailyProjects(temp);

        }
    }
}
