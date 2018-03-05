package com.xyy.simplehomework.view.fragments.semester;

import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/4.
 */

class ViewModel {
    private List<Week> weekList;

    ViewModel() {
        weekList = new ArrayList<>();

        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            weekList.add(new Week(i));
        }
    }

    List<Week> getWeekList() {
        return weekList;
    }
}

