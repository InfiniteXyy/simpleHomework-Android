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
    private List<MySubject> subjectList;
    private ObjectBoxLiveData<Homework> homeworkObjectBoxLiveData;
    private Box<Homework> homeworkBox;

    public WeekViewModel() {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        homeworkBox = boxStore.boxFor(Homework.class);
        subjectList = boxStore.boxFor(MySubject.class).getAll();
    }


    public List<MySubject> getSubjectList() {
        return subjectList;
    }

    public void putHomework(Homework homework) {
        homework.weekIndex = DateHelper.getWeekIndex();
        homeworkBox.put(homework);
    }

    public ObjectBoxLiveData<Homework> getHomeworkLiveData() {
        if (homeworkObjectBoxLiveData == null) {
            homeworkObjectBoxLiveData = new ObjectBoxLiveData<>(homeworkBox.query()
                    .equal(Homework_.weekIndex, DateHelper.getWeekIndex())
                    .order(Homework_.deadline) // default sort by deadline
                    .build());
        }
        return homeworkObjectBoxLiveData;
    }
}
