package com.xyy.simplehomework.view.handler;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.Calendar;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectClickHandler {
    private Context mContext;
    public ProjectClickHandler(Context context) {
        mContext = context;
    }
    public void setPlan(Homework homework) {
        ProjectViewModel viewModel = ProjectViewModel.getInstance();
        viewModel.setCurrentHomework(homework);
        Calendar now = Calendar.getInstance();
        Calendar plan;
        if (homework.planDate != null) {
            plan = Calendar.getInstance();
            plan.setTime(homework.planDate);
        } else {
            plan = now;
        }

        DatePickerDialog dialog = DatePickerDialog.newInstance(
                viewModel,
                plan.get(Calendar.YEAR),
                plan.get(Calendar.MONTH),
                plan.get(Calendar.DAY_OF_MONTH)
        );
        dialog.setTitle("计划时间");
        dialog.setFirstDayOfWeek(1);
        dialog.show(((Activity) mContext).getFragmentManager(), null);
        dialog.vibrate(false);
        dialog.setMinDate(now);
        now.setTime(homework.deadline);
        dialog.setMaxDate(now);
    }
}
