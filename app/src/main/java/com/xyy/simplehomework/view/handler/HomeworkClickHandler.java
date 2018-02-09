package com.xyy.simplehomework.view.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.xyy.simplehomework.view.HomeworkActivity;

/**
 * Created by xyy on 2018/2/9.
 */

public class HomeworkClickHandler {
    public void showDetail(View view, long id) {
        Intent intent = new Intent(view.getContext(), HomeworkActivity.class);
        intent.putExtra(HomeworkActivity.HOMEWORK_ID, id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Pair<View, String> shareCard = Pair.create(view, "sharedCard");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) view.getContext(),
                    shareCard
            );
            view.getContext().startActivity(intent, options.toBundle());
        } else {
            view.getContext().startActivity(intent);
        }
    }
}
