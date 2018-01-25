package com.xyy.simplehomework.cards;


import com.xyy.simplehomework.cards.MyProject;
import com.xyy.simplehomework.cards.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewManager {
    public List<RecyclerViewFragment> recyclerViewFragments;
    List<MyProject> projects;

    public RecyclerViewManager() {
        projects = new ArrayList<>();
        recyclerViewFragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recyclerViewFragments.add(new RecyclerViewFragment());
        }
    }

    public void updateProjects(List<MyProject> projects) {
        this.projects = projects;
        for (RecyclerViewFragment rvf : recyclerViewFragments) {
            rvf.updateDailyProjects(projects);
        }
    }


}
