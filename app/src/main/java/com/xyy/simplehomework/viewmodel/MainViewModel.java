package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.model.DataServer;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.Date;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/5.
 */

public class MainViewModel {
    private static MainViewModel instance;
    private Week week;
    private Semester semester;
    private DataServer dataServer;
    private Context mContext;
    private BoxStore boxStore;

    public MainViewModel(Context context) {
        mContext = context;
        instance = this;
        // set up data server
        boxStore = ((App) context.getApplicationContext()).getBoxStore();

        dataServer = new DataServer(boxStore);
        dataServer.resetAll();

        // get week and semester
        semester = getThisSemester();
        week = getThisWeek(semester);
        DateHelper.setUp(week, semester);

        useDemo(week, semester);
    }

    public static MainViewModel getInstance() {
        return instance;
    }


    public Week getWeek() {
        return week;
    }

    public Semester getSemester() {
        return semester;
    }

    private Week getThisWeek(Semester semester) {
        int weekIndex = DateHelper.getTimeBetween(semester.startDate, new Date(), DateHelper.WEEK);
        for (Week week : semester.weeks) {
            if (week.weekIndex == weekIndex) {
                return week;
            }
        }
        Week week = new Week();
        week.weekIndex = weekIndex;
        week.semester.setTarget(semester);
        dataServer.put(week);
        return week;
    }

    private Semester getThisSemester() {
        Semester semester = dataServer.findSemester();
        if (semester == null) {
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 0, 1);
            semester.endDate = new Date(118, 10, 2);
            dataServer.put(semester);
        }
        return semester;
    }

    private void useDemo(Week week, Semester semester) {
        Semester thisSemester = getThisSemester();

        MySubject[] subjects = {
                new MySubject("计算机系统", mContext.getResources().getColor(R.color.japanBrown)),
                new MySubject("高等数学", mContext.getResources().getColor(R.color.japanBlue)),
                new MySubject("线性代数", mContext.getResources().getColor(R.color.japanPink)),
                new MySubject("离散数学", mContext.getResources().getColor(R.color.japanTea)),
                new MySubject("概率论", mContext.getResources().getColor(R.color.japanOrange)),
        };
        for (MySubject subject : subjects) {
            subject.semester.setTarget(thisSemester);
            subject.availableWeeks = new byte[]{6, 7, 8, 9};
            thisSemester.allSubjects.add(subject);
        }
        thisSemester.allSubjects.applyChangesToDb();
        // refresh this week homework
        week.homeworks.reset();
        // homework demo
        int i = 0;
        for (MySubject subject : subjects) {
            if (i < 3) {
                Homework homework = new Homework(subject.getName() + "练习" + (i + 1), DateHelper.afterDays(9 - i));
                homework.week.setTarget(week);
                homework.subject.setTarget(subject);
                homework.setDetail("这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情");
                dataServer.put(homework);
            }
            i++;
            Homework homework = new Homework(subject.getName() + "计划练习", DateHelper.afterDays(i + 2));
            homework.subject.setTarget(subject);
            homework.week.setTarget(week);
            homework.setPlanDate(DateHelper.getToday());
            homework.setDetail("这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情");
            dataServer.put(homework);
        }
    }

}
