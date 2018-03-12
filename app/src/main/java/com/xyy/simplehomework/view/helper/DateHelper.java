package com.xyy.simplehomework.view.helper;

import com.xyy.simplehomework.entity.Semester;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;

import java.util.Date;

/**
 * Created by xyy on 2018/1/29.
 */

public class DateHelper {
    public final static int WEEK = 1;
    public final static int DAY = 2;
    public final static String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public final static String[] num2cn = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    public final static String[] semesters = {
            "一年级", "两年级", "三年级", "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三",
            "大一", "大二", "大三", "大四",
            "研一", "研二", "研三"
    };
    private final static String[] afterDate = {"今天", "明天", "后天"};
    private final static String[] weekDay = {"一", "二", "三", "四", "五", "六", "日"};
    public static Date date = new Date();
    private static DateTime firstDayOfThisWeek;
    private static int weekIndex = -1;
    private static int semesterIndex = -1;
    private static int termIndex = -1;
    private static DateTime nowTime = new DateTime().withTimeAtStartOfDay();

    public static void setUpSemester(Semester semester) {
        weekIndex = getTimeBetween(semester.startDate, date, WEEK);
        semesterIndex = semester.grade;
        termIndex = semester.term;
    }

    public static Date afterDays(int i) {
        return nowTime.plusDays(i).toDate();
    }

    public static int getTimeBetween(Date start, Date fin, int type) {
        DateTime startDateTime = new DateTime(start);
        DateTime finalDateTime = new DateTime(fin);
        if (type == WEEK)
            return Weeks.weeksBetween(startDateTime, finalDateTime).getWeeks();
        else if (type == DAY)
            return Days.daysBetween(startDateTime, finalDateTime).getDays();
        else return 0;
    }

    public static String getWeekTitle() {
        return getWeekTitle(weekIndex);
    }

    public static String getWeekTitle(int index) {
        return "第" + num2cn[(index + 1)] + "周";
    }

    public static int getWeekIndex() {
        return weekIndex;
    }

    public static Date getToday() {
        return nowTime.toDate();
    }

    public static String afterDayFormat(Date date) {
        DateTime thisDate = new DateTime(date);
        int days = Days.daysBetween(nowTime, thisDate).getDays();
        if (days < 0)
            return "已逾期";
        else {
            if (days < 3)
                return afterDate[days];
            else {
                if (firstDayOfThisWeek == null)
                    firstDayOfThisWeek = nowTime.dayOfWeek().withMinimumValue();
                int daysAfterThisWeekMonday = Days.daysBetween(firstDayOfThisWeek, thisDate).getDays();
                if (daysAfterThisWeekMonday < 7)
                    return "本周" + weekDay[daysAfterThisWeekMonday];
                else if (daysAfterThisWeekMonday < 14)
                    return "下周" + weekDay[daysAfterThisWeekMonday - 7];
                else
                    return num2cn[daysAfterThisWeekMonday / 7] + "周后";
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(afterDayFormat(afterDays(i)));
        }
    }
}


