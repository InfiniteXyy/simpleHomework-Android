package com.xyy.simplehomework.view.fragments.week;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.BindingAdapter;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkAddBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class AddDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, AddDialogHandler {
    private static final int REQUEST_CODE_CHOOSE = 23;
    private WeekUIInteraction mListener;
    private Homework homework;
    private MySubject subject;

    public AddDialog() {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    public static AddDialog newInstance(MySubject subject) {
        AddDialog fragment = new AddDialog();
        fragment.subject = subject;
        return fragment;
    }

    @BindingAdapter({"android:photoUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
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
        homework = new Homework();
        DialogHomeworkAddBinding binding = DialogHomeworkAddBinding.inflate(inflater, container, false);
        homework.weekIndex = DateHelper.getWeekIndex();
        homework.setDeadline(new Date());
        binding.setHomework(homework);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // first, set spinner
        if (savedInstanceState != null)
            homework.setImgUri(savedInstanceState.getString("img"));
        final AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        List<MySubject> subjects = mListener.getSubjectList();
        ArrayAdapter<MySubject> arrayAdapter =
                new ArrayAdapter<MySubject>(getContext(),
                        android.R.layout.simple_spinner_item,
                        subjects);
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        if (subject != null)
            spinner.setSelection(subjects.indexOf(subject));
        // second, set DatePicker
        View.OnClickListener showCalendar = v -> {
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
        };

        view.findViewById(R.id.timeBtn).setOnClickListener(showCalendar);


        // finally, set toolbar listener
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());

        view.findViewById(R.id.button).setOnClickListener(v -> {
            if (homework.getTitle() == null) {
                Toast.makeText(getContext(), "请正确填写信息", Toast.LENGTH_SHORT).show();
            } else if (!homework.getTitle().trim().equals("")) {
                homework.subject.setTarget((MySubject) spinner.getSelectedItem());
                mListener.putHomework(homework);
                dismiss();
            } else {
                Toast.makeText(getContext(), "请正确填写信息", Toast.LENGTH_SHORT).show();
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
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Matisse.from(AddDialog.this)
                                    .choose(MimeType.of(MimeType.JPEG))
                                    .capture(true)
                                    .captureStrategy(
                                            new CaptureStrategy(true, "com.xyy.simpleHomework.fileprovider"))
                                    .maxSelectable(1)
                                    .gridExpectedSize(
                                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE);
                        } else {
                            Toast.makeText(getContext(), "请在系统设置中打开权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            homework.setImgUri(Matisse.obtainPathResult(data).get(0));
        }
    }
}
