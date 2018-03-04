package com.xyy.simplehomework.view.fragments.semester;

import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.entity.Week_;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

/**
 * Created by xyy on 2018/3/4.
 */

class ViewModel {
    private List<Week> weekList;

    ViewModel() {
        // first, get reference of ObjectBox
        Box<Week> box = App.getInstance().getBoxStore().boxFor(Week.class);
        // second, set week list
        weekList = new ArrayList<>();
        Query<Week> query = box.query().equal(Week_.weekIndex, 0).build();
        Semester semester = MainViewModel.getInstance().getSemester();

        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            Week week;
            week = query.setParameter(Week_.weekIndex, i).findFirst();
            if (week == null) {
                week = new Week(i);
                week.semester.setTarget(semester);
                box.put(week);
            }
            weekList.add(week);
        }
    }

    List<Week> getWeekList() {
        return weekList;
    }
}

