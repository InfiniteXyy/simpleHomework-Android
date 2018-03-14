package com.xyy.simplehomework.view.fragments.week;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkAddBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, AddDialogHandler {


    private static final String TAG = "AddDialog";
    private static final int RESULT_CAPTURE_IMAGE = 1;
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
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // first, set spinner
        Log.d("123", "onViewCreated: "+homework);
        if (savedInstanceState != null)
            homework.setImgUri(savedInstanceState.getString("img"));
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
                dpd.vibrate(false);// 禁止震动
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


    @Override
    public void setImg(View view) {
        String fileName = new SimpleDateFormat("/yyMMddmm").format(new Date()) + ".jpg";
        String filePath = Environment.getExternalStorageDirectory().getPath() + fileName;
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(filePath)));
        startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
        homework.setImgUri(filePath);
    }

    @BindingAdapter({"android:photoUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("img", homework.imgUri);
    }
}
