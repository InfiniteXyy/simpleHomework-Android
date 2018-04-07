package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.App;

import java.util.Stack;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/5.
 */

public class MainViewModel {
    private static MainViewModel instance;
    private Context mContext;
    private BoxStore boxStore;
    private Stack<Homework> homeworkStack;

    public MainViewModel(Context context) {
        mContext = context;
        // set up data server
        boxStore = ((App) context.getApplicationContext()).getBoxStore();

        homeworkStack = new Stack<>();
        instance = this;
    }

    public static MainViewModel getInstance() {
        return instance;
    }

    public void appendHomework(Homework homework) {
        homeworkStack.push(homework);
    }

    public void popHomework() {
        Box<Homework> box = boxStore.boxFor(Homework.class);
        while (!homeworkStack.empty()) {
            box.put(homeworkStack.pop());
        }
    }

}
