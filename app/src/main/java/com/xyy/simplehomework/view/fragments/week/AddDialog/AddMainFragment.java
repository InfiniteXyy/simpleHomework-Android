package com.xyy.simplehomework.view.fragments.week.AddDialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.DateHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMainFragment extends Fragment {

    public AddMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_add_dialog_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.title);
        TextView subTitle = view.findViewById(R.id.subtitle);
        title.setText(((HomeworkAddDialog) getParentFragment()).getTitle());
        subTitle.setText(DateHelper.getWeekTitle());
        TextInputEditText editText = view.findViewById(R.id.dateSetter);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SingleDateAndTimePickerDialog.Builder(getContext())
                        .bottomSheet()
                        .curved()
                        .build()
                        .display();
            }
        });
    }


}
