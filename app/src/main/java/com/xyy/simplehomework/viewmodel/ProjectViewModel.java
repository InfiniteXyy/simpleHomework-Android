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

public class ProjectViewModel {
    private DataServer dataServer;
    private Context mContext;

    public ProjectViewModel(Context context) {
        mContext = context;
        // getData
        BoxStore boxStore = ((App) context).getBoxStore();
        dataServer = new DataServer(boxStore);
        dataServer.useDemo();
        dataServer.bindToDateHelper();
    }

    public List<MyProject> getAllProjects() {
        return dataServer.getAllProjects();
    }
}
