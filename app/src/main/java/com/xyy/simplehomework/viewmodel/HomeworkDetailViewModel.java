package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.App;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/9.
 */

public class HomeworkDetailViewModel {
    private static HomeworkDetailViewModel viewModel;
    private Context mContext;
    private Homework homework;
    private BoxStore boxStore;

    public HomeworkDetailViewModel(Context context, long id) {
        mContext = context;
        boxStore = App.getInstance().getBoxStore();
        homework = boxStore.boxFor(Homework.class).get(id);
        viewModel = this;
    }

    public Homework getHomework() {
        return homework;
    }

}
