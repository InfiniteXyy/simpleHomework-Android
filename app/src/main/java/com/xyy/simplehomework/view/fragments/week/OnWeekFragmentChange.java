package com.xyy.simplehomework.view.fragments.week;

/**
 * Created by xyy on 2018/2/22.
 */

public interface OnWeekFragmentChange {
    void onChangeBack();

    void onChangeToSubject();

    void startSubjectActivity(long subject_id);

    void openAddDialog();
}
