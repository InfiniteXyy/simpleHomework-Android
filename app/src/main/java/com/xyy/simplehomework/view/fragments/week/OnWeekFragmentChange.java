package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.MySubject;

/**
 * Created by xyy on 2018/2/22.
 */

public interface OnWeekFragmentChange {
    void onChangeBack();

    void onChangeToSubject();

    void onClickSubject(MySubject subject);

    void setWaitingForAdd(boolean waitingForAdd);
}
