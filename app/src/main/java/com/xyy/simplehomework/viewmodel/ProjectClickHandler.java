package com.xyy.simplehomework.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.ProjectActivity;
import com.xyy.simplehomework.view.SettingActivity;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectClickHandler {

    public void showDetail(View view) {
        Intent intent = new Intent(view.getContext(), ProjectActivity.class);
        intent.putExtra("123", "计算机网络");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) view.getContext(),
                view.findViewById(R.id.smallSubjectTitle),
                "shareTitle"
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getContext().startActivity(intent, options.toBundle());
        } else {
            view.getContext().startActivity(intent);
        }
    }

    public void finishProject(View view, MyProject project) {
        project.setStatus(MyProject.TYPE_PROJECT_FIN);
    }
}
