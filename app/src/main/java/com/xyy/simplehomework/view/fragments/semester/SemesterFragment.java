package com.xyy.simplehomework.view.fragments.semester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;

/**
 * Created by xyy on 2018/1/29.
 */

public class SemesterFragment extends Fragment {
    public final static String TAG = "SemesterFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_semester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ViewGroup container = view.findViewById(R.id.layout);
//        // set transition
//        LayoutTransition transition = new LayoutTransition();
//        transition.setDuration(300);
//        container.setLayoutTransition(transition);

    }
}
