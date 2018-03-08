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
    public final static String[] num2cn = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public final static String[] weekInCn = {
            "周日", "周一", "周二", "周三", "周四", "周五", "周六"
    };
    public final static String[] afterDate = {"今天", "明天", "后天", "三", "四", "五", "六", "日", "下周", "周后"};
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

        DateTime s = new DateTime(start);
        DateTime f = new DateTime(fin);
        if (type == WEEK) {

            return Weeks.weeksBetween(s,f).getWeeks();
        }
        else if (type == DAY)
            return Days.daysBetween(s,f).getDays();
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

    public static Date getToday() {
        return afterDays(0);
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
                int weekBet= Days.daysBetween(nowTime.dayOfWeek().withMinimumValue(), thisDate).getDays();
                if (weekBet < 8)
                    return "本周" + afterDate[weekBet];
                else {
                    if (weekBet >= 8 && weekBet < 15)
                        return afterDate[7];
                    else
                        return weekBet / 7 + afterDate[8];
                }
            }


        }
    }
}

}
