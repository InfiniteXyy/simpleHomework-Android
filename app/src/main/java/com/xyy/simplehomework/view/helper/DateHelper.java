package com.xyy.simplehomework.view.helper;

import com.xyy.simplehomework.entity.Semester;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyy on 2018/1/29.
 */

public class DateHelper {
    public final static int WEEK = 1;
    public final static int DAY = 2;
    public final static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public final static String[] num2cn = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public final static String[] weekInCn = {
            "周日", "周一", "周二", "周三", "周四", "周五", "周六"
    };
    public final static String[] semesters = {
            "一年级", "两年级", "三年级", "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三",
            "大一", "大二", "大三", "大四",
            "研一", "研二", "研三"
    };
    public static Date date = new Date();

    private static int weekIndex = -1;
    private static int semesterIndex = -1;
    private static int termIndex = -1;

    public static void setUpSemester(Semester semester) {
        weekIndex = getTimeBetween(semester.startDate, date, WEEK);
        semesterIndex = semester.grade;
        termIndex = semester.term;
    }

    public static Date afterDays(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getTimeBetween(Date start, Date fin, int type) {
        long a = start.getTime();
        long b = fin.getTime();
        if (type == WEEK)
            return (int) ((b - a) / 1000 / 60 / 60 / 24 / 7);
        else if (type == DAY)
            return (int) ((b - a) / 1000 / 60 / 60 / 24);
        else return 0;
    }

    public static String getWeekTitle() {
        return getWeekTitle(weekIndex);
    }

    public static String getWeekTitle(int index) {
        return "第" + num2cn[index + 1] + "周";
    }

    public static int getWeekIndex() {
        return weekIndex;
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
