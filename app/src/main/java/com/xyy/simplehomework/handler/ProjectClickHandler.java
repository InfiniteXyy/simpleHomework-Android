package com.xyy.simplehomework.handler;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.xyy.simplehomework.SettingActivity;
import com.xyy.simplehomework.entity.MyProject;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectClickHandler {

    public void showDetail(View view) {
        Intent intent = new Intent(view.getContext(), SettingActivity.class);
//        intent.putExtra(ProjectActivity.PROJECT_ID, projectId);
        view.getContext().startActivity(intent);
    }
    public void finishProject(View view, MyProject project) {
        project.setStatus(MyProject.TYPE_PROJECT_FIN);
    }
}
