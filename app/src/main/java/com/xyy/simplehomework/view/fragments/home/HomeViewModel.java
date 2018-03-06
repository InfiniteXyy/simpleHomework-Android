package com.xyy.simplehomework.view.fragments.home;

import android.arch.lifecycle.ViewModel;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

/**
 * Created by xyy on 2018/3/3.
 */

public class HomeViewModel extends ViewModel {
    private ObjectBoxLiveData<Homework> homeworkObjectBoxLiveData;
    private Box<Homework> homeworkBox;


    public HomeViewModel() {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        homeworkBox = boxStore.boxFor(Homework.class);
    }

    public ObjectBoxLiveData<Homework> getHomeworkLiveData() {
        if (homeworkObjectBoxLiveData == null) {
            homeworkObjectBoxLiveData = new ObjectBoxLiveData<>(homeworkBox.query()
                    .greater(Homework_.planDate, DateHelper.afterDays(-1))
                    .order(Homework_.planDate)
                    .build());
        }
        return homeworkObjectBoxLiveData;
    }

}