package com.xyy.simplehomework.view.fragments.home.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;

import java.util.Arrays;

/**
 * A Task Dialog for adding external plan
 * e.g. you can add 'Run 3km' to your tomorrow plan
 * also some suggested plan are included
 */

public class DialogTask extends DialogFragment {
    public DialogTask() {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_task_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_task, Arrays.asList("12", "34")) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });
        view.findViewById(R.id.close_btn).setOnClickListener((v) -> dismiss());
    }
}
