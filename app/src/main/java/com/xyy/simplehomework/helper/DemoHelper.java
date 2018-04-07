package com.xyy.simplehomework.helper;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.view.App;

import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * A Util for homework and subject demo
 */

public class DemoHelper {
    public static void useDemo(Semester semester, int weekIndex) {
        MySubject[] subjects = {
                new MySubject("计算机系统"),
                new MySubject("高等数学"),
                new MySubject("线性代数"),
                new MySubject("离散数学"),
                new MySubject("概率论")
        };
        BoxStore boxStore = App.getInstance().getBoxStore();
        for (MySubject subject : subjects) {
            subject.semester.setTarget(semester);
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
        for (int i = 0; i <= weekIndex; i++) {
            for (MySubject subject : subjects) {
                for (int j = 0; j < random.nextInt(2) + 2; j++) {
                    if (random.nextBoolean()) {
                        Homework homework = new Homework(names[random.nextInt(names.length)], DateHelper.afterDays(random.nextInt(10)));
                        homework.subject.setTarget(subject);
                        homework.weekIndex = i;
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
