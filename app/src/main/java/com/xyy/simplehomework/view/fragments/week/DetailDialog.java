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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkDetailBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.Date;

/**
 * Created by xyy on 2018/2/27.
 */

public class DetailDialog extends DialogFragment {
    public View.OnClickListener clickClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
    private Homework homework;
    private boolean isSettingPlan = false;
    public View.OnClickListener clickSetPlan = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isSettingPlan) {
                getChildFragmentManager().popBackStack();
            } else {
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_right)
                        .add(R.id.container, CalendarFragment.newInstance(homework.planDate))
                        .addToBackStack(null)
                        .commit();
            }
            isSettingPlan = !isSettingPlan;
            ((Button) v).setText(isSettingPlan ? "确认" : "设置计划");
        }
    };

    public DetailDialog() {
        setStyle(STYLE_NORMAL, R.style.InfoDialog);
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

    public Homework getHomework() {
        return homework;
    }

    private void setHomework(Homework homework) {
        this.homework = homework;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //   mListener.putHomework(homework);
    }

    public static class CalendarFragment extends Fragment {
        private Date planDate;
        private MaterialCalendarView calendarView;
        private Homework homework;

        public static CalendarFragment newInstance(Date planDate) {
            CalendarFragment calendarFragment = new CalendarFragment();
            calendarFragment.planDate = planDate == null ? new Date() : planDate;
            return calendarFragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            homework = ((DetailDialog) getParentFragment()).getHomework();
            return inflater.inflate(R.layout.dialog_homework_detail_setplan, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            calendarView = view.findViewById(R.id.calendarView);
            calendarView.setDateSelected(planDate, true);
            calendarView.state().edit()
                    .setMinimumDate(DateHelper.date)
                    .commit();
            calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    homework.setPlanDate(calendarView.getSelectedDate().getDate());
                }
            });
        }
    }
}
