package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkAddBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.fragments.week.WeekUIInteraction;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import org.w3c.dom.Text;

import me.relex.circleindicator.CircleIndicator;


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
        // TODO: add homework logic
        homework = new Homework();
        binding.setHomework(homework);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // first, set spinner
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        mListener.getViewModel().getSubjectNameList());
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // second, set toolbar listener
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
                mListener.getViewModel().putHomework(homework);
                dismiss();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
