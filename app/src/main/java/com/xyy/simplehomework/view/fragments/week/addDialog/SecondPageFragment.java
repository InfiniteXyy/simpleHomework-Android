package com.xyy.simplehomework.view.fragments.week.addDialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondPageFragment extends Fragment {


    public SecondPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_add_dialog_two, container, false);
    }

}
