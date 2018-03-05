package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.SubjectActivity;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment implements WeekUIInteraction {
    public static final String TAG = "WeekFragment";
    private static final int WEEK_PAGE = 0;
    private static final int SUBJECT_PAGE = 1;
    private static MaterialMenuDrawable menuDrawable;
    private int currentPage;
    private Toolbar toolbar;
    private SubjectFragment subjectFragment;
    private DetailFragment detailFragment;
    private TextFragment textFragment;
    private ViewModel viewModel;
    private View textBtn;
    private View imgBtn;

    @Override
    public void onAttach(Context context) {
        viewModel = new ViewModel(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // first, init toolbar
        if (menuDrawable == null) {
            menuDrawable = new MaterialMenuDrawable(getContext(),
                    Color.BLACK,
                    MaterialMenuDrawable.Stroke.THIN);
        }
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(menuDrawable);
        toolbar.setTitle(DateHelper.getWeekTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == WEEK_PAGE) {
                    if (getContext() != null)
                        ((MainActivity) getContext()).showDrawer();
                } else {
                    onChangeToDetail();
                }
            }
        });
        // second, init child fragments
        subjectFragment = SubjectFragment.newInstance(0);
        detailFragment = new DetailFragment();
        textFragment = new TextFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment)
                .add(R.id.fragment_week, textFragment)
                .hide(textFragment)
                .commit();


        // third, set changeBtn between textFragment and defaultFragment
        textBtn = view.findViewById(R.id.textBtn);
        imgBtn = view.findViewById(R.id.imgBtn);
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change to Text mode
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(textFragment)
                        .hide(detailFragment)
                        .addToBackStack(null)
                        .commit();
                imgBtn.setVisibility(View.VISIBLE);
                textBtn.setVisibility(View.GONE);
            }
        });
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().popBackStack();
                textBtn.setVisibility(View.VISIBLE);
                imgBtn.setVisibility(View.GONE);
            }
        });
    }

    public void setHomeworkList(List<Homework> list) {
        detailFragment.setList(list);
        textFragment.setList();
    }

    @Override
    public void onChangeToDetail() {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .hide(subjectFragment)
                .show(detailFragment)
                .commit();
        currentPage = WEEK_PAGE;
        toolbar.setTitle(DateHelper.getWeekTitle());
        textBtn.setVisibility(View.VISIBLE);
        menuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER);
    }

    @Override
    public void onChangeToSubject() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (!subjectFragment.isAdded()) {
            transaction.add(R.id.fragment_week, subjectFragment);
        }
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .hide(detailFragment)
                .show(subjectFragment)
                .commit();
        currentPage = SUBJECT_PAGE;
        toolbar.setTitle("科目列表");
        textBtn.setVisibility(View.GONE);
        menuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
    }

    @Override
    public void onClickSubject(MySubject subject) {
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        intent.putExtra(SubjectActivity.SUBJECT_ID, subject.id);
        startActivity(intent);
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
    public ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subjectFragment = null;
        detailFragment = null;
        textFragment = null;
        viewModel.onDestroy();
        viewModel = null;
    }


}
