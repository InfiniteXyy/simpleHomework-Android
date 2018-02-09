package com.xyy.simplehomework.view.handler;

import android.content.Intent;
import android.view.View;

import com.xyy.simplehomework.view.ProjectActivity;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectClickHandler {

    public void showDetail(View view, long id) {
        Intent intent = new Intent(view.getContext(), ProjectActivity.class);
        intent.putExtra(ProjectActivity.PROJECT_ID, id);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Pair<View, String> shareCard = Pair.create((View) view.getParent(), "sharedCard");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) view.getContext(),
                    shareCard
            );
            view.getContext().startActivity(intent, options.toBundle());
        } else {
            view.getContext().startActivity(intent);
        }*/
        view.getContext().startActivity(intent);

    }
}
