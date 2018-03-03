package com.xyy.simplehomework.view.fragments.home;

import com.xyy.simplehomework.entity.Day;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by xyy on 2018/3/3.
 */

class ViewModel {
    private List<Day> dayList;

    ViewModel() {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        // second, init day list(today and days this week)
        dayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayList.add(new Day(DateHelper.afterDays(i)));
        }
        // third, set homework list of days
        Query<Homework> query = boxStore.boxFor(Homework.class).query().equal(Homework_.planDate, new Date()).build();
        for (Day day : dayList) {
            day.setHomeworkList(query.setParameter(Homework_.planDate, day.getDate()).find());
        }
    }

    List<Day> getDayList() {
        return dayList;
    }
}