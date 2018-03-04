package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.DialogHomeworkDetailBinding;
import com.xyy.simplehomework.entity.Homework;

/**
 * Created by xyy on 2018/2/27.
 */

public class DetailDialog extends DialogFragment {
    private Homework homework;

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
}
