package com.xyy.simplehomework.helper;

import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Semester_;
import com.xyy.simplehomework.view.App;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;

import java.util.Date;

import io.objectbox.Box;

/**
 * A util class for Date management based on joda-time
 */

public class DateHelper {
    public final static String[] num2cn = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private final static int WEEK = 1;
    private final static int DAY = 2;
    private final static String[] afterDate = {"今天", "明天", "后天"};
    private final static String[] weekDay = {"一", "二", "三", "四", "五", "六", "日"};
    private static DateHelper instance;
    private static DateTime nowTime = new DateTime().withTimeAtStartOfDay();
    private int weekIndex = -1;

    /**
     * used to set up semester manually
     * after set, the instance is changed to this semester, must pay attention
     *
     * @param semester a semester before this semester
     */

    public static DateHelper getInstance(Semester semester) {
        DateHelper dateHelper = new DateHelper();
        dateHelper.weekIndex = getTimeBetween(semester.startDate, new Date(), WEEK);
        instance = dateHelper;
        return dateHelper;
    }

    public static DateHelper getInstance() {
        if (instance == null) {
            instance = getInstance(getSemester(new Date()));
        }
        return instance;
    }

    public static Date afterDays(int i) {
        return nowTime.plusDays(i).toDate();
    }

    private static int getTimeBetween(Date start, Date fin, int type) {
        DateTime startDateTime = new DateTime(start);
        DateTime finalDateTime = new DateTime(fin);
        if (type == WEEK)
            return Weeks.weeksBetween(startDateTime, finalDateTime).getWeeks();
        else if (type == DAY)
            return Days.daysBetween(startDateTime, finalDateTime).getDays();
        else return 0;
    }

    public static String getWeekTitle(int index) {
        return "第" + num2cn[(index + 1)] + "周";
    }

    public static Date getToday() {
        return nowTime.toDate();
    }

    public static int afterDayNum(Date date) {
        return Days.daysBetween(nowTime, new DateTime(date)).getDays();
    }

    private static Semester getSemester(Date date) {
        Box<Semester> box = App.getInstance().getBoxStore().boxFor(Semester.class);
        Semester semester = box
                .query()
                .less(Semester_.startDate, date)
                .greater(Semester_.endDate, date)
                .build()
                .findFirst();
        if (semester == null) {
            semester = new Semester();
            semester.startDate = DateTime.parse("2018-2-18").toDate();
            semester.endDate = DateTime.parse("2018-7-6").toDate();
            box.put(semester);
            DemoHelper.useDemo(semester, getTimeBetween(semester.startDate, nowTime.toDate(), WEEK));
        }
        return semester;
    }

    public String getWeekTitle() {
        return getWeekTitle(weekIndex);
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public String afterDayFormat(Date date) {
        DateTime thisDate = new DateTime(date);
        int days = Days.daysBetween(nowTime, thisDate).getDays();
        if (days < 0)
            return "已逾期";
        else {
            if (days < 3)
                return afterDate[days];
            else {
                DateTime firstDayOfThisWeek = nowTime.dayOfWeek().withMinimumValue();
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
}


