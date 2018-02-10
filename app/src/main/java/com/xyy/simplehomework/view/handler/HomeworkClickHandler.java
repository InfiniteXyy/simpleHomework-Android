package com.xyy.simplehomework.view.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.HomeworkActivity;
import com.xyy.simplehomework.viewmodel.ProjectDetailViewModel;

import java.util.Calendar;

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

    public void showMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.project_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.set_plan:
                        Calendar now = Calendar.getInstance();
                        ProjectDetailViewModel viewModel = ProjectDetailViewModel.getInstance();
                        DatePickerDialog dialog = DatePickerDialog.newInstance(
                                viewModel,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );
                        dialog.setAccentColor(viewModel.getColor());
                        dialog.show(viewModel.getFragmentManger(), "选择日期");
                        dialog.vibrate(false);
                        dialog.setMinDate(now);
                        now.add(Calendar.DATE, 7);
                        dialog.setMaxDate(now);
                        break;
                    case R.id.finish_work:
                        break;
                }
                return false;
            }
        });
    }
}
