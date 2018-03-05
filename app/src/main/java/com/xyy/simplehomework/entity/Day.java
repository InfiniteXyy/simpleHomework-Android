package com.xyy.simplehomework.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/3/1.
 */

public class Day {
    private static final String[] week = {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    };
    private Calendar cal;
    private Date date;
    private List<Homework> homeworkList;

    public Day(Date date) {
        this.date = date;
        cal = Calendar.getInstance();
        cal.setTime(date);
        this.homeworkList = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public String getDayOfMonth() {
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }

    public String getDayOfWeek() {
        return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }
}
