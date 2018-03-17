package com.xyy.simplehomework.view.fragments.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

/**
 * Created by xyy on 2018/3/3.
 */

public class HomeViewModel extends ViewModel {
    private ObjectBoxLiveData<Homework> homeworkObjectBoxLiveData;
    private Box<Homework> homeworkBox;
    private Box<MySubject> subjectBox;
    private LiveData<List<MySubject>> subjectLiveData;


    public HomeViewModel() {
        // first, get reference of ObjectBox
        BoxStore boxStore = App.getInstance().getBoxStore();
        homeworkBox = boxStore.boxFor(Homework.class);
        subjectBox = boxStore.boxFor(MySubject.class);
    }

    public List<MySubject> getSubjectList() {
        return subjectBox.getAll();
    }


    public void putSubject(MySubject subject) {
        subjectBox.put(subject);
    }

    public LiveData<List<MySubject>> getSubjectLiveData() {
        if (subjectLiveData == null) {
            subjectLiveData = new ObjectBoxLiveData<>(subjectBox.query().build());
        }
        return subjectLiveData;
    }
}