package com.xyy.simplehomework.utils;

import com.xyy.simplehomework.entity.Semester;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyy on 2018/1/29.
 */

public class DateHelper {
    public static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static String[] semesters = {
            "一年级", "两年级", "三年级", "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三",
            "大一", "大二", "大三", "大四",
            "研一", "研二", "研三"
    };
    public Date date;
    private int weekIndex = 0;
    private int dayIndex = 0;
    private Semester semester;
    private Calendar calendar = Calendar.getInstance();

    public DateHelper() {
        this.date = new Date();
    }

    public int getWeeksAfter(Date when) {
        calendar.setTime(date);
        int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(when);
        int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        weekIndex = endWeek - startWeek;
        return weekIndex;
    }

    public int getDayIndexOfWeek(Date when) {
        // 需要判断截止日期是否在本周的问题
        calendar.setTime(when);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
    public Date afterDays(int days) {
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public String getDayName() {
        calendar.setTime(date);
        dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weeks[dayIndex];
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public int getSemesterIndex() {
        return semester.grade;
    }

    public String getSemesterName() {
        return semesters[semester.grade] + (semester.term == 0 ? "：上" : "：下");
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
