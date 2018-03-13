package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.MySubject;

import java.util.List;

/**
 * Created by xyy on 2018/3/5.
 */

public class Week {
    public int weekIndex;
    private List<MySubject> subjects;

    public Week(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public List<MySubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MySubject> subjects) {
        this.subjects = subjects;
    }

    public String getWeekName() {
        return "第" + (weekIndex + 1) + "周";
    }
}
