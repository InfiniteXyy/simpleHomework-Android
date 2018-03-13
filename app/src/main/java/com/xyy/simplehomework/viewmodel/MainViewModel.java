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
    private DataServer dataServer;
    private Context mContext;
    private BoxStore boxStore;

    public MainViewModel(Context context) {
        mContext = context;
        // set up data server
        boxStore = ((App) context.getApplicationContext()).getBoxStore();

        dataServer = new DataServer(boxStore);
        dataServer.resetAll();

        // get semester
        Semester semester = getThisSemester();
        DateHelper.setUpSemester(semester);

        useDemo(semester);
    }

    private Semester getThisSemester() {
        Semester semester = dataServer.findSemester();
        if (semester == null) {
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 1, 16);
            semester.endDate = new Date(118, 7, 6);
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
            subject.availableWeeks = new byte[]{6, 7, 8, 9, 10};
            semester.allSubjects.add(subject);
        }
        semester.allSubjects.applyChangesToDb();

        Box<Homework> homeworkBox = boxStore.boxFor(Homework.class);
        // homework demo
        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            for (MySubject subject : subjects) {
                Homework homework = new Homework(subject.getName() + "练习周=" + i, DateHelper.afterDays(3));
                homework.subject.setTarget(subject);
                homework.setDetail("这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情这是详情");
                homework.weekIndex = i;
                homeworkBox.put(homework);

                Homework homework2 = new Homework(subject.getName() + "计划=" + i, DateHelper.afterDays(2));
                homework2.subject.setTarget(subject);
                homework2.weekIndex = i;
                if (i % 2 == 0) homework2.status = Homework.FINISHED;

                homework2.setPlanDate(DateHelper.getToday());
                homework2.setDetail("这是计划这是计划这是计划这是计划这是计划这是计划这是计划这是计划这是计划");
                homeworkBox.put(homework2);
            }
        }

    }

}
