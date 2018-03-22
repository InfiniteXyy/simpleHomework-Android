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
import java.util.Random;
import java.util.Stack;

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
    private Stack<Homework> homeworkStack;

    public MainViewModel(Context context) {
        mContext = context;
        // set up data server
        boxStore = ((App) context.getApplicationContext()).getBoxStore();

        dataServer = new DataServer(boxStore);
        dataServer.resetAll();

        // get setting
        Semester semester = getThisSemester();
        DateHelper.setUpSemester(semester);
        homeworkStack = new Stack<>();
        useDemo(semester);
        instance = this;
    }

    public static MainViewModel getInstance() {
        return instance;
    }

    public void appendHomework(Homework homework) {
        homeworkStack.push(homework);
    }

    public void popHomework() {
        Box<Homework> box = boxStore.boxFor(Homework.class);
        while (!homeworkStack.empty()) {
            box.put(homeworkStack.pop());
        }
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
        Random random = new Random();
        String names[] = {
                "完成一张卷子",
                "复习准备月考",
                "看ppt",
                "见图片",
                "继续做项目",
                "写一篇作文"
        };
        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            for (MySubject subject : subjects) {
                for (int j = 0; j < random.nextInt(2)+2; j++) {
                    if (random.nextBoolean()) {
                        Homework homework = new Homework(names[random.nextInt(names.length)], DateHelper.afterDays(random.nextInt(10)));
                        homework.subject.setTarget(subject);
                        homework.weekIndex = i;
                        homework.type = random.nextInt(3);
                        if (i <= 2 || random.nextInt(5) <= 1) {
                            homework.setFinished(true);
                        } else {
                            if (random.nextBoolean()) {
                                homework.setPlanDate(DateHelper.afterDays(random.nextInt(4)));
                            }
                        }
                        homeworkBox.put(homework);
                    }
                }
            }
        }
    }
}
