package com.xyy.simplehomework.viewmodel;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.App;

import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/8.
 */

public class ProjectDetailViewModel implements DatePickerDialog.OnDateSetListener{
    private Context mContext;
    private MyProject project;
    private BoxStore boxStore;
    private static ProjectDetailViewModel viewModel;
    private List<Homework> homeworkList;
    public ProjectDetailViewModel(Context context, long id) {
        mContext = context;
        boxStore = App.getInstance().getBoxStore();
        project = boxStore.boxFor(MyProject.class).get(id);
        viewModel = this;
    }

    public List<Homework> getHomeworkList() {
        homeworkList = project.homework;
        return homeworkList;
    }

    public int getColor() { return mContext.getResources().getColor(project.subject.getTarget().colorId); }

    public MyProject getProject() {
        return project;
    }

    public FragmentManager getFragmentManger() { return ((Activity) mContext).getFragmentManager(); }

    public static ProjectDetailViewModel getInstance() { return viewModel; }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("haha", "onDateSet: Year "+year+" Month "+monthOfYear+" Day "+dayOfMonth);
        homeworkList.get(0).status = 10086; // for a test
    }
}
