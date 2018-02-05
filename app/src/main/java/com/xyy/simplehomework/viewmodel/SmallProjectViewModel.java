package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.model.DataServer;
import com.xyy.simplehomework.view.App;

import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/5.
 */

public class SmallProjectViewModel {
    DataServer dataServer;
    private Context mContext;
    private List<MyProject> projectList;

    public SmallProjectViewModel(Context context) {
        // getData
        BoxStore boxStore = ((App) context.getApplicationContext()).getBoxStore();
        dataServer = new DataServer(boxStore);
    }

    public List<MyProject> getAllProjects() {
        projectList = dataServer.getAllProjects();
        return projectList;
    }
}
