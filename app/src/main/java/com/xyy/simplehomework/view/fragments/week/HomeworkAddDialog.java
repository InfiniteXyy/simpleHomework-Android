package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkAddBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.DateHelper;


public class HomeworkAddDialog extends DialogFragment {
    private static final String TAG = "HomeworkAddDialog";
    private WeekUIInteraction mListener;
    private Homework homework;

    public HomeworkAddDialog() {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof WeekUIInteraction) {
            mListener = (WeekUIInteraction) getParentFragment();
        } else {
            throw new RuntimeException("parent Fragment should implement WeekUIInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DialogHomeworkAddBinding binding = DialogHomeworkAddBinding.inflate(inflater, container, false);
        homework = new Homework();
        binding.setHomework(homework);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // first, set spinner
        final AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<MySubject> arrayAdapter =
                new ArrayAdapter<MySubject>(getContext(),
                        android.R.layout.simple_spinner_item,
                        mListener.getViewModel().getSubjectList());
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // second, set DatePicker
        final MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setMinimumDate(DateHelper.date)
                .commit();
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                homework.setDeadline(date.getDate());
            }
        });

        // finally, set toolbar listener
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homework.subject.setTarget((MySubject) spinner.getSelectedItem());
                mListener.getViewModel().putHomework(homework);
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
