package com.xyy.simplehomework.view.fragments.week;

import android.support.annotation.NonNull;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

/**
 * Created by xyy on 2018/2/27.
 */

class ViewModel {
    private List<MySubject> subjectList;
    private Box<Homework> homeworkBox;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    ViewModel(final WeekFragment weekFragment) {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();

        // second, get needed List (Homework and Subjects of this week)
        homeworkBox = boxStore.boxFor(Homework.class);

        Query<Homework> homeworkQuery = boxStore.boxFor(Homework.class)
                .query()
                .equal(Homework_.weekIndex, DateHelper.getWeekIndex())
                .order(Homework_.deadline) // default sort by deadline
                .build();

        homeworkQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<Homework>>() {
                    @Override
                    public void onData(@NonNull List<Homework> homework) {
                        weekFragment.setHomeworkList(homework);
                    }
                });

        subjectList = boxStore.boxFor(MySubject.class).getAll();
    }


    List<MySubject> getSubjectList() {
        return subjectList;
    }

    void putHomework(Homework homework) {
        homework.weekIndex = DateHelper.getWeekIndex();
        homeworkBox.put(homework);
    }

    void onDestroy() {
        subscriptions.cancel();
    }
}
