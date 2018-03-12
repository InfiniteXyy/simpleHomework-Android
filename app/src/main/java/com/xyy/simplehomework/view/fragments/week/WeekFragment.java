package com.xyy.simplehomework.view.fragments.week;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment implements WeekUIInteraction {
    public static final String TAG = "WeekFragment";
    private boolean isText = false;
    private DetailFragment detailFragment;
    private TextFragment textFragment;
    private WeeksFragment weeksFragment;
    private WeekViewModel viewModel;
    private ImageView imgBtn;
    private FloatingActionButton fab;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) fab.hide();
        else fab.show();
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(WeekViewModel.class);
        // first, init Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(DateHelper.getWeekTitle());
        // second, init child fragments
        detailFragment = new DetailFragment();
        textFragment = TextFragment.newInstance(viewModel.getSubjectList());
        weeksFragment = new WeeksFragment();
        weeksFragment.setWeeks(viewModel.getWeeks());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment)
                .add(R.id.fragment_week, textFragment)
                .add(R.id.fragment_week, weeksFragment)
                .hide(weeksFragment)
                .hide(textFragment)
                .commit();

        imgBtn = view.findViewById(R.id.button);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isText) {
                    getChildFragmentManager().popBackStack();
                    imgBtn.setImageResource(R.drawable.ic_text_fields_black_24px);
                } else {
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                            .show(textFragment)
                            .hide(detailFragment)
                            .addToBackStack(null)
                            .commit();
                    imgBtn.setImageResource(R.drawable.ic_web_black_24px);
                }
                isText = !isText;
            }
        });

        View weekBtn = view.findViewById(R.id.appBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.scale_in, R.anim.scale_out, R.anim.scale_in_re, R.anim.scale_out_re)
                        .show(weeksFragment)
                        .hide(detailFragment)
                        .addToBackStack(null)
                        .commit();
                imgBtn.setVisibility(View.GONE);
            }
        });

        //finally bind view model to sub fragments
        viewModel.getHomeworkLiveData().observe(this, new Observer<List<Homework>>() {
            @Override
            public void onChanged(@Nullable List<Homework> homework) {
                detailFragment.setHomeworkList(homework);
                textFragment.updateHomeworkList();
            }
        });
    }

    @Override
    public void onClickHomework(Homework homework) {
        DetailDialog.newInstance(homework).show(getChildFragmentManager(), null);
    }

    @Override
    public void showAddDialog() {
        new AddDialog().show(getChildFragmentManager(), null);
    }

    @Override
    public List<MySubject> getSubjectList() {
        return viewModel.getSubjectList();
    }

    @Override
    public void putHomework(Homework homework) {
        viewModel.putHomework(homework);
    }

}
