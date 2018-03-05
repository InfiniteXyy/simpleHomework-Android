package com.xyy.simplehomework.view.fragments.home;

import android.util.Log;

import com.xyy.simplehomework.entity.Day;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

/**
 * Created by xyy on 2018/3/3.
 */

class ViewModel {
    private List<Day> dayList;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    ViewModel(final HomeFragment fragment) {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        // second, init day list(today and days this week)
        dayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayList.add(new Day(DateHelper.afterDays(i)));
        }
        // third, set homework list of days
        Query<Homework> query = boxStore.boxFor(Homework.class).query().equal(Homework_.planDate, new Date()).build();
        for (final Day day : dayList) {
            query.setParameter(Homework_.planDate, day.getDate())
                    .subscribe().on(AndroidScheduler.mainThread())
                    .observer(new DataObserver<List<Homework>>() {
                        @Override
                        public void onData(List<Homework> data) {
                            day.setHomeworkList(data);
                            Log.d("123", "onData: day = " + day.getDayOfMonth() + " & size = " + data.size());
                            fragment.updateDayData();
                        }
                    });
        }
    }

    List<Day> getDayList() {
        return dayList;
    }

    void onDestroy() {
        Log.d("123", "onDestroy: " + subscriptions.getActiveSubscriptionCount());
        subscriptions.cancel();
    }
}