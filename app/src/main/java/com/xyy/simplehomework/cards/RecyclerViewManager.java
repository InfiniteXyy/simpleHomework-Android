package com.xyy.simplehomework.cards;


import android.util.Log;

import com.xyy.simplehomework.entity.MyProject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xyy on 2018/1/24.
 */

public class RecyclerViewManager {
    final private static int weekRange = 5;
    public List<RecyclerViewFragment> recyclerViewFragments;
    private int[] dailyCounts;
    public RecyclerViewManager() {
        recyclerViewFragments = new ArrayList<>();
        for (int i = 0; i < weekRange; i++) {
            recyclerViewFragments.add(new RecyclerViewFragment());
        }
        dailyCounts = new int[weekRange];
    }

    public void updateProjects(List<MyProject> projects) {
        for (int i = 0; i < weekRange; i++) {
            List<MyProject> temp = new ArrayList<>();
            for (MyProject myProject : projects) {
                if (myProject.testDate == i)
                    temp.add(myProject);
            }
            recyclerViewFragments.get(i).updateDailyProjects(temp);
            dailyCounts[i] = temp.size();
        }
    }

    public int getDailyNum(int day) {
        return dailyCounts[day];
    }
}
