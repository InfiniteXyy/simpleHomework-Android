package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/27.
 */

class ViewModel {
    private Context mContext;
    private BoxStore boxStore;
    private List<Homework> homeworkList;
    private List<MySubject> subjectList;

    ViewModel(Context context) {
        // first, get reference of ObjectBox
        boxStore = App.getInstance().getBoxStore();

        // TODO: should only get homework and subject This Week (Use DateHelper)
        // second, get needed List (Homework and Subjects of this week)
        homeworkList = boxStore.boxFor(Homework.class).getAll();
        subjectList = boxStore.boxFor(MySubject.class).getAll();
    }

    List<Homework> getHomeworkList() {
        return homeworkList;
    }

    List<MySubject> getSubjectList() {
        return subjectList;
    }

    List<String> getSubjectNameList() {
        List<String> nameList = new ArrayList<>();
        for (MySubject subject : subjectList) {
            nameList.add(subject.getName());
        }
        return nameList;
    }

    void putHomework(Homework homework) {
        boxStore.boxFor(Homework.class).put(homework);
    }
}
