package com.xyy.simplehomework.view.fragments.week.detailDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;

/**
 * Created by xyy on 2018/2/27.
 */

public class MainDetailDialog extends DialogFragment {

    public MainDetailDialog() {
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_Dialog);
    }

    public static MainDetailDialog newInstance(Homework homework) {
        return new MainDetailDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_homework, container, false);
    }
}
