package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.content.Intent;
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
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.SubjectActivity;
import com.xyy.simplehomework.view.fragments.week.AddDialog.HomeworkAddDialog;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment implements OnWeekFragmentChange, View.OnClickListener {
    private static final int WEEK_PAGE = 0;
    private static final int SUBJECT_PAGE = 1;
    private static MaterialMenuDrawable menuDrawable;
    private int pageStatus;
    private Toolbar toolbar;
    private SubjectFragment subjectFragment;
    private DetailFragment detailFragment;
    private Context mContext;
    private List<MySubject> subjects;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        // show all subjects(should put week id, use 0 for test)
        if (menuDrawable == null) {
            menuDrawable = new MaterialMenuDrawable(mContext,
                    getResources().getColor(android.R.color.secondary_text_light),
                    MaterialMenuDrawable.Stroke.THIN);
        }
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(menuDrawable);
        toolbar.setTitle(DateHelper.getWeekTitle());
        toolbar.setNavigationOnClickListener(this);

        subjectFragment = SubjectFragment.newInstance(0);
        detailFragment = new DetailFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment).commit();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onChangeBack() {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .hide(subjectFragment)
                .show(detailFragment)
                .commit();
        pageStatus = WEEK_PAGE;
        toolbar.setTitle(DateHelper.getWeekTitle());
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
        pageStatus = SUBJECT_PAGE;
        toolbar.setTitle("科目列表");
        menuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW);
    }

    public List<MySubject> getSubjectList() {
        if (subjects == null) {
            subjects = App.getInstance().getBoxStore().boxFor(MySubject.class).getAll();
        }
        return subjects;
    }

    public List<String> getSubjectNameList() {
        List<String> name = new ArrayList<>();
        for (MySubject subject : subjects) {
            name.add(subject.getName());
        }
        return name;
    }

    @Override
    public void onClickSubject(MySubject subject) {
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        intent.putExtra(SubjectActivity.SUBJECT_ID, subject.id);
        startActivity(intent);
    }

    @Override
    public void showAddDialog() {
        new HomeworkAddDialog().show(getChildFragmentManager(), null);
    }

    @Override
    public void onClick(View v) {
        // home button click
        if (pageStatus == WEEK_PAGE) {
            ((MainActivity) mContext).showDrawer();
        } else {
            onChangeBack();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subjectFragment = null;
        detailFragment = null;
    }
}
