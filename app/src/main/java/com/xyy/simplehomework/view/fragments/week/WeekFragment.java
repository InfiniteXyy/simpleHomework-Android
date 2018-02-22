package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.SubjectActivity;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment implements OnWeekFragmentChange {
    private SubjectFragment subjectFragment;
    private DetailFragment detailFragment;
    private Context mContext;

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
        subjectFragment = SubjectFragment.newInstance(0);
        detailFragment = new DetailFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment).commit();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onChangeBack() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.hide(subjectFragment).show(detailFragment).commit();
        ((MainActivity) mContext).setHomeButton(false);
    }

    @Override
    public void onChangeToSubject() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        if (!subjectFragment.isAdded()) {
            transaction.add(R.id.fragment_week, subjectFragment);
        }
        transaction.hide(detailFragment).show(subjectFragment).commit();
        ((MainActivity) mContext).setHomeButton(true);
    }

    @Override
    public void startSubjectActivity(long subject_id) {
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        intent.putExtra(SubjectActivity.SUBJECT_ID, subject_id);
        startActivity(intent);
    }
}
