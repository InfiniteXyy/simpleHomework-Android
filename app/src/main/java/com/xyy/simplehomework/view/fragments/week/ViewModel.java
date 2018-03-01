package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.MainActivity;

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
    private WeekFragment fragment;

    ViewModel(Context context) {
        // first, get reference of ObjectBox
        boxStore = App.getInstance().getBoxStore();

        // TODO: should only get homework and subject This Week (Use DateHelper)
        // second, get needed List (Homework and Subjects of this week)
        week = ((MainActivity) context).viewModel.getWeek();
        fragment = (WeekFragment) ((MainActivity) context)
                .getSupportFragmentManager()
                .findFragmentByTag(WeekFragment.TAG);
        homeworkList = week.homeworks;
        subjectList = boxStore.boxFor(MySubject.class).getAll();
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
        fragment.updateUI();
    }
}
