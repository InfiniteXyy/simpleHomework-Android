package com.xyy.simplehomework.viewmodel;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;

import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/8.
 */

public class ProjectDetailViewModel implements DatePickerDialog.OnDateSetListener {
    private static ProjectDetailViewModel viewModel;
    private Context mContext;
    private MyProject project;
    private MySubject subject;
    private BoxStore boxStore;
    private List<Homework> homeworkList;

    public ProjectDetailViewModel(Context context, long id) {
        mContext = context;
        boxStore = App.getInstance().getBoxStore();
        project = boxStore.boxFor(MyProject.class).get(id);
        subject = project.subject.getTarget();
        viewModel = this;
    }

    public static ProjectDetailViewModel getInstance() {
        return viewModel;
    }

    public List<Homework> getHomeworkList() {
        homeworkList = project.homework;
        return homeworkList;
    }

    public int getColor() {
        return mContext.getResources().getColor(subject.colorId);
    }

    public MyProject getProject() {
        return project;
    }

    public MySubject getSubject() {
        return subject;
    }

    public FragmentManager getFragmentManger() {
        return ((Activity) mContext).getFragmentManager();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    public void test() {
        subject.setName("sb");
        boxStore.boxFor(MySubject.class).put(subject);
    }
}
