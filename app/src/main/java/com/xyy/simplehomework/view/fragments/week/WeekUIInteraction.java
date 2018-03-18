package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;

import java.util.List;

/**
 * Created by xyy on 2018/2/22.
 */

public interface WeekUIInteraction {
    void onClickWeek(int weekIndex);

    void needBtns(boolean need);

    void showAddDialog();

    void showAddDialog(MySubject subject);

    List<MySubject> getSubjectList();

    void putHomework(Homework homework);
}
