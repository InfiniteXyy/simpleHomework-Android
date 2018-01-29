package com.xyy.simplehomework.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;

/**
 * Created by xyy on 2018/1/29.
 */

public class AddProjectDialog extends MaterialDialog {
    public AddProjectDialog(final Context context) {

        super(new MaterialDialog.Builder(context)
                .title(R.string.choose_subject)
                .items(((MainActivity) context).getSubjectNames())
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        new MaterialDialog.Builder(context)
                                .title(R.string.choose_date)
                                .items(DateHelper.weeks)
                                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        final int selectedDate = which;
                                        new MaterialDialog.Builder(context)
                                                .title(R.string.decide_name)
                                                .input("Homework name", "", false, new MaterialDialog.InputCallback() {
                                                    @Override
                                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//                                                        MyProject myProject = new MyProject(input.toString());
//                                                        myProject.testDate = selectedDate;
//                                                        myProject.subject.setTarget(selectedSubject);
//                                                        projectBox.put(myProject);
                                                    }
                                                })
                                                .build()
                                                .show();
                                        return false;
                                    }
                                })
                                .positiveText("choose")
                                .build()
                                .show();
                        return false;
                    }
                })
                .positiveText("choose"));
    }
}
