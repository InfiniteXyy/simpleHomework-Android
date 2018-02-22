package com.xyy.simplehomework.view.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.Calendar;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectClickHandler {
    public void showDetail(View view, Homework homework) {
        Context mContext = view.getContext();
        //final Dialog dialog = new Dialog(mContext, R.style.BottomDialog);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        ViewDataBinding binding = DataBindingUtil.inflate(((Activity) mContext).getLayoutInflater(),
                R.layout.dialog_homework, (ViewGroup) view.findViewById(R.id.dialog), false);
        dialog.setView(binding.getRoot());
        binding.setVariable(BR.handler, this);
        binding.setVariable(BR.homework, homework);
        dialog.show();
    }

    // TODO: 将两个dialog结合
    public void setPlan(View view, Homework homework) {
        final Context mContext = view.getContext();
        final ProjectViewModel viewModel = ProjectViewModel.getInstance();
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
        dialog.show(((Activity) mContext).getFragmentManager(), null);
        dialog.vibrate(false);
        dialog.setMinDate(now);
        now.setTime(homework.deadline);
        dialog.setMaxDate(now);
    }
}
