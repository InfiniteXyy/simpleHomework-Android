package com.xyy.simplehomework.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/3/1.
 */

public class Day {
    private Date date;
    private List<Homework> homeworkList;

    public Day(Date date) {
        this.date = date;
        this.homeworkList = new ArrayList<>();
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }
}
