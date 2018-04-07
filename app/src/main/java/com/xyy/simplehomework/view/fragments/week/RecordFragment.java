package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.MyFragment;

/**
 * Created by xyy on 2018/3/24.
 */

public class RecordFragment extends MyFragment {
    WeekFragment weekFragment = new WeekFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.mainFragment, weekFragment)
                .commit();
    }

    public void showAddDialog(MySubject subject) {
        //    AddDialog.getInstance(null).show(getChildFragmentManager(), null);
        AddDialog addDialog = AddDialog.newInstance(subject, weekFragment);
        addDialog.setmListener(weekFragment);
        Slide slideTransition = new Slide(Gravity.BOTTOM);
        slideTransition.setDuration(300);
        addDialog.setEnterTransition(slideTransition);
        addDialog.setExitTransition(slideTransition);
        getChildFragmentManager().beginTransaction()
                .add(R.id.mainFragment, addDialog)
                .hide(weekFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        weekFragment.needBtns(!hidden);
    }

    @Override
    public void onBackPressed() {
        if (weekFragment.getChildFragmentManager().getBackStackEntryCount() != 0)
            weekFragment.getChildFragmentManager().popBackStack();
        else getChildFragmentManager().popBackStack();
    }
}
