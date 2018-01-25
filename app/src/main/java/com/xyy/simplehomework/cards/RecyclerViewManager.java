package com.xyy.simplehomework.cards;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewManager {
    public List<RecyclerViewFragment> recyclerViewFragments;
    public RecyclerViewManager() {
        recyclerViewFragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recyclerViewFragments.add(new RecyclerViewFragment());
        }
    }

    public void updateProjects(List<MyProject> projects) {
        for (int i = 0; i < 5; i++) {
            List<MyProject> temp = new ArrayList<>();
            for (MyProject myProject : projects) {
                if (myProject.testDate == i)
                    temp.add(myProject);
            }
            recyclerViewFragments.get(i).updateDailyProjects(temp);
        }
    }
}
