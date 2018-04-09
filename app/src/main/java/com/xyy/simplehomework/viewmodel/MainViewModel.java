package com.xyy.simplehomework.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;

import java.util.List;
import java.util.Stack;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

/**
 * Created by xyy on 2018/2/5.
 */

public class MainViewModel extends ViewModel {
    private Box<MySubject> subjectBox;
    private Box<Homework> homeworkBox;
    private LiveData<List<MySubject>> subjectLiveData;
    private ObjectBoxLiveData<Homework> homeworkLiveData;

    public MainViewModel() {
        BoxStore boxStore = App.getInstance().getBoxStore();
        subjectBox = boxStore.boxFor(MySubject.class);
        homeworkBox = boxStore.boxFor(Homework.class);
    }

    public List<MySubject> getSubjectList() {
        return subjectBox.getAll();
    }

    public List<Homework> getHomeworkList() {
        return homeworkBox.getAll();
    }

    public void putSubject(MySubject subject) {
        subjectBox.put(subject);
    }
    public void putHomework(Homework homework) {
        homeworkBox.put(homework);
    }

    public LiveData<List<MySubject>> getSubjectLiveData() {
        if (subjectLiveData == null) {
            subjectLiveData = new ObjectBoxLiveData<>(subjectBox.query().build());
        }
        return subjectLiveData;
    }

    public LiveData<List<Homework>> getHomeworkLiveData() {
        if (homeworkLiveData == null) {
            homeworkLiveData = new ObjectBoxLiveData<>(homeworkBox.query().build());
        }
        return homeworkLiveData;
    }

}
