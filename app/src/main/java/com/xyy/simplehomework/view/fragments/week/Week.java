package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.Homework;

import java.util.List;

/**
 * Created by xyy on 2018/3/5.
 */

public class Week {
    public int weekIndex;
    private List<Homework> homeworkList;
    private boolean finished = true;
    private int num = 0;

    public Week(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public String getProgress() {
        int allSize = homeworkList.size();
        return (allSize - num) + "/" + homeworkList.size();
    }

    public boolean hasFinished() {
        return finished;
    }


    public String getWeekName() {
        return "第" + (weekIndex + 1) + "周";
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
        num = 0;
        for (Homework homework : homeworkList) {
            if (!homework.getFinished()) {
                finished = false;
                num++;
            }
        }
    }
}
