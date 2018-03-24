package com.xyy.simplehomework.view.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by xyy on 2018/3/24.
 */

public class MyFragment extends Fragment {
    public void onBackPressed() {
        getChildFragmentManager().popBackStack();
    }
}
