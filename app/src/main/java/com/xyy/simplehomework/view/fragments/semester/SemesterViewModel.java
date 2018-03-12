package com.xyy.simplehomework.view.fragments.semester;

import android.arch.lifecycle.ViewModel;

import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.fragments.week.Week;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import io.objectbox.query.Query;
import io.objectbox.query.QueryFilter;

/**
 * Created by xyy on 2018/3/4.
 */

public class SemesterViewModel extends ViewModel {
    private List<Week> weekList;

    public SemesterViewModel() {
        // TODO: 解决一下取得每一个
        weekList = new ArrayList<>();
        Query<MySubject> subjectQuery = App
                .getInstance()
                .getBoxStore()
                .boxFor(MySubject.class)
                .query()
                .build();
        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            Week temp = new Week(i);
            temp.setSubjects(subjectQuery.find());
            weekList.add(temp);
        }
    }

    List<Week> getWeekList() {
        return weekList;
    }
}

