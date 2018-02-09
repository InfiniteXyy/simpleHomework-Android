package com.xyy.simplehomework.view.helper;

import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Week;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyy on 2018/1/29.
 */

public class DateHelper {
    public final static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public final static String[] semesters = {
            "一年级", "两年级", "三年级", "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三",
            "大一", "大二", "大三", "大四",
            "研一", "研二", "研三"
    };
    public static Date date = new Date();
    private static Calendar calendar = Calendar.getInstance();

    private static int weekIndex = -1;
    private static int semesterIndex = -1;
    private static int termIndex = -1;

    public static void setUp(Week week, Semester semester) {
        weekIndex = week.weekIndex;
        semesterIndex = semester.grade;
        termIndex = semester.term;
    }

    public static int getWeeksBetween(Date start, Date fin) {
        long a = start.getTime();
        long b = fin.getTime();
        return (int) ((b - a) / 1000 / 60 / 60 / 24 / 7);
    }

    public static int getDayIndexOfWeek(Date when) {
        calendar.setTime(when);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getDayName() {
        return weeks[getDayIndex()];
    }

    public static int getWeekIndex() {
        return weekIndex;
    }

    public static int getDayIndex() {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static String getSemesterName() {
        return semesters[semesterIndex] + (termIndex == 0 ? "：上" : "：下");
    }

    public static Date afterDays(int a) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, a);
        return calendar.getTime();
    }

}
