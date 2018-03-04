package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkDetailBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.text.SimpleDateFormat;

/**
 * Created by xyy on 2018/2/27.
 */

public class DetailDialog extends DialogFragment {
    private Homework homework;
    private boolean isSettingPlan = false;

    public DetailDialog() {
        setStyle(STYLE_NORMAL, R.style.InfoDialog);
    }

    private void setHomework(Homework homework) {
        this.homework = homework;
    }

    public static DetailDialog newInstance(Homework homework) {
        DetailDialog detailDialog = new DetailDialog();
        detailDialog.setHomework(homework);
        return detailDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogHomeworkDetailBinding binding = DialogHomeworkDetailBinding.inflate(inflater, container, false);
        binding.setHomework(homework);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View.OnClickListener clickClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public View.OnClickListener clickSetPlan = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isSettingPlan) {
                getChildFragmentManager().popBackStack();
            } else {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.container, new CalendarFragment())
                        .addToBackStack(null)
                        .commit();
            }
            isSettingPlan = !isSettingPlan;
            ((Button) v).setText(isSettingPlan ? "确认" : "设置计划");
        }
    };

    public static class CalendarFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_homework_detail_setplan, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
            final TextView textView = view.findViewById(R.id.text);
            calendarView.state().edit()
                    .setMinimumDate(DateHelper.date)
                    .commit();
            calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    textView.setText(new SimpleDateFormat("M月d日").format(date.getDate()));
                }
            });
        }
    }
}
