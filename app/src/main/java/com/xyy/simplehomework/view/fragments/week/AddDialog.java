package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkAddBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;

import java.util.Calendar;
import java.util.Date;


public class AddDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddDialog";
    private WeekUIInteraction mListener;
    private Homework homework;

    public AddDialog() {
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
        homework.setDeadline(new Date());
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
                        mListener.getSubjectList());
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // second, set DatePicker
        View.OnClickListener showCalendar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar deadline = Calendar.getInstance();
                deadline.setTime(homework.deadline);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddDialog.this,
                        deadline.get(Calendar.YEAR),
                        deadline.get(Calendar.MONTH),
                        deadline.get(Calendar.DAY_OF_MONTH)
                );
                deadline.setTime(new Date());
                dpd.setMinDate(deadline);
                dpd.show(getActivity().getFragmentManager(), null);
            }
        };

        view.findViewById(R.id.timeBtn).setOnClickListener(showCalendar);

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
                mListener.putHomework(homework);
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        homework.setDeadline(new Date(year - 1900, monthOfYear, dayOfMonth));
    }


}
