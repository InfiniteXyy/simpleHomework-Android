package com.xyy.simplehomework.view.fragments.week.addDialog;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.xyy.simplehomework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainAddFragment extends Fragment {

    public MainAddFragment() {
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
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        new String[]{"计算机系统", "计算机网络"});
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

    }


}
