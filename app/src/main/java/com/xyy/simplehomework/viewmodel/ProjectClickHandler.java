package com.xyy.simplehomework.viewmodel;

import android.content.Intent;
import android.view.View;

import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.SettingActivity;

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
