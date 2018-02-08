package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.App;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/8.
 */

public class ProjectDetailViewModel {
    private Context mContext;
    private MyProject project;
    private BoxStore boxStore;
    public ProjectDetailViewModel(Context context, long id) {
        mContext = context;
        boxStore = App.getInstance().getBoxStore();
        project = boxStore.boxFor(MyProject.class).get(id);
    }

    public MyProject getProject() {
        return project;
    }
}
