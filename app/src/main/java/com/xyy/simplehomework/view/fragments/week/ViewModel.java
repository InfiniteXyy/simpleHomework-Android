package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.util.Log;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.entity.Week_;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/27.
 */

class ViewModel {
    private BoxStore boxStore;
    private List<Homework> homeworkList;
    private List<MySubject> subjectList;
    private Week week;

    ViewModel(Context context) {
        // first, get reference of ObjectBox
        boxStore = App.getInstance().getBoxStore();

        // TODO: should only get homework and subject This Week (Use DateHelper)
        // second, get needed List (Homework and Subjects of this week)
        week = ((MainActivity) context).viewModel.getWeek();

        homeworkList = week.homeworks;
        subjectList = boxStore.boxFor(MySubject.class).getAll();
    }

    long getWeekId() {
        return week.id;
    }

    List<Homework> getHomeworkList() {
        return homeworkList;
    }

    List<MySubject> getSubjectList() {
        return subjectList;
    }

    void putHomework(Homework homework) {
        week.homeworks.add(homework);
        week.homeworks.applyChangesToDb();
    }
}
