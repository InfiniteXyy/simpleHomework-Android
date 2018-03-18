package com.xyy.simplehomework.view.fragments.week;

import android.arch.lifecycle.ViewModel;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

/**
 * Created by xyy on 2018/2/27.
 */

public class WeekViewModel extends ViewModel {
    private Box<MySubject> subjectBox;
    private ObjectBoxLiveData<Homework> homeworkObjectBoxLiveData;
    private Box<Homework> homeworkBox;
    private int weekIndex;
    private ObjectBoxLiveData<Homework> allHomeworkObjectBoxLiveData;

    public WeekViewModel() {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        homeworkBox = boxStore.boxFor(Homework.class);
        weekIndex = DateHelper.getWeekIndex();
        subjectBox = boxStore.boxFor(MySubject.class);
    }

    public void setWeekIndex(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public List<MySubject> getSubjectList() {
        return subjectBox.getAll();
    }

    public void putHomework(Homework homework) {
        homeworkBox.put(homework);
    }

    public ObjectBoxLiveData<Homework> getHomeworkLiveData() {
        if (homeworkObjectBoxLiveData == null) {
            homeworkObjectBoxLiveData = new ObjectBoxLiveData<>(homeworkBox.query()
                    .equal(Homework_.weekIndex, weekIndex)
                    .order(Homework_.deadline) // default sort by deadline
                    .build());
        }
        return homeworkObjectBoxLiveData;
    }

    public ObjectBoxLiveData<Homework> getAllHomeworkLiveData() {
        if (allHomeworkObjectBoxLiveData == null) {
            allHomeworkObjectBoxLiveData = new ObjectBoxLiveData<>(homeworkBox.query()
                    .build());
        }
        return allHomeworkObjectBoxLiveData;
    }

    public List<Homework> getHomeworkData(int index) {
        return homeworkBox.query()
                .equal(Homework_.weekIndex, index)
                .order(Homework_.deadline)
                .build()
                .find();
    }

    public List<Homework> getHomeworkData() {
        return homeworkBox.getAll();
    }
}
