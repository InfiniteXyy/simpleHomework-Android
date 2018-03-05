package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.model.DataServer;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/5.
 */

public class MainViewModel {
    private static MainViewModel instance;
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

        // get semester
        Semester semester = getThisSemester();
        DateHelper.setUpSemester(semester);

        useDemo(semester);
    }

    public static MainViewModel getInstance() {
        return instance;
    }


    private Semester getThisSemester() {
        Semester semester = dataServer.findSemester();
        if (semester == null) {
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 0, 1);
            semester.endDate = new Date(118, 10, 2);
            boxStore.boxFor(Semester.class).put(semester);
        }
        return semester;
    }

    private void useDemo(Semester semester) {
        MySubject[] subjects = {
                new MySubject("计算机系统", mContext.getResources().getColor(R.color.japanBrown)),
                new MySubject("高等数学", mContext.getResources().getColor(R.color.japanBlue)),
                new MySubject("线性代数", mContext.getResources().getColor(R.color.japanPink)),
                new MySubject("离散数学", mContext.getResources().getColor(R.color.japanTea)),
                new MySubject("概率论", mContext.getResources().getColor(R.color.japanOrange)),
        };
        for (MySubject subject : subjects) {
            subject.semester.setTarget(semester);
            subject.availableWeeks = new byte[]{6, 7, 8, 9};
            semester.allSubjects.add(subject);
        }
        semester.allSubjects.applyChangesToDb();

        int weekIndex = DateHelper.getWeekIndex();
        Box<Homework> homeworkBox = boxStore.boxFor(Homework.class);
        // homework demo
        int i = 0;
        for (MySubject subject : subjects) {
            if (i < 3) {
                Homework homework = new Homework(subject.getName() + "练习" + (i + 1), DateHelper.afterDays(9 - i));
                homework.subject.setTarget(subject);
                homework.setDetail("这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情");
                homework.weekIndex = weekIndex;
                homeworkBox.put(homework);
            }
            i++;
            Homework homework = new Homework(subject.getName() + "计划练习", DateHelper.afterDays(i + 2));
            homework.subject.setTarget(subject);
            homework.weekIndex = weekIndex;
            homework.setPlanDate(DateHelper.getToday());
            homework.setDetail("这是计划这是计划这是计划这是计划这是计划这是计划这是计划这是计划这是计划");
            homeworkBox.put(homework);
        }
    }

}
