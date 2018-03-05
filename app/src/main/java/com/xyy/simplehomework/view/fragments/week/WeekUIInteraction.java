package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;

/**
 * Created by xyy on 2018/2/22.
 */

public interface WeekUIInteraction {
    void onChangeToDetail();

    void onChangeToSubject();

    void onClickSubject(MySubject subject);

    void onClickHomework(Homework homework);

    void showAddDialog();

    ViewModel getViewModel();

}
