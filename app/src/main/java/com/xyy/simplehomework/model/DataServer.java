package com.xyy.simplehomework.model;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Semester_;

import java.util.Date;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/2.
 */

public class DataServer {
    private BoxStore boxStore;

    public DataServer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public void resetAll() {
        boxStore.boxFor(MySubject.class).removeAll();
        boxStore.boxFor(Semester.class).removeAll();
        boxStore.boxFor(Homework.class).removeAll();
    }

    public Semester findSemester() {
        Date date = new Date();
        return boxStore.boxFor(Semester.class).query()
                .greater(Semester_.endDate, date)
                .less(Semester_.startDate, date)
                .build().findFirst();
    }
}
