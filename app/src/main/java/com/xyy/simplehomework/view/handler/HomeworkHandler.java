package com.xyy.simplehomework.view.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.fragments.week.CommentFragment;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyy on 2018/3/18.
 */

public class HomeworkHandler implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "HomeworkHandler";
    private Context mContext;
    private MainViewModel viewModel;
    private Homework homework;
    private Animation fadeIn;
    private Animation fadeOut;
    private Animation alphaIn;
    private Animation alphaOut;
    private View lastView;
    private Transition transition;

    public HomeworkHandler(Context context, MainViewModel viewModel) {
        this.mContext = context;
        this.viewModel = viewModel;
        fadeIn = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
        fadeIn.setDuration(200);
        fadeOut.setDuration(200);
        alphaIn = AnimationUtils.loadAnimation(mContext, R.anim.alpha_in);
        alphaOut = AnimationUtils.loadAnimation(mContext, R.anim.alpha_out);

        transition = new ChangeBounds();
        transition.setInterpolator(new FastOutLinearInInterpolator());
        transition.setDuration(200);
    }

    public void onClickHomework(View view, Homework homework) {
        if (homework.getFinished()) return;
        RecyclerView recyclerView = (RecyclerView) view.getParent();
        TransitionManager.endTransitions(recyclerView);
        if (lastView != view) {
            showDetail(view, true, true);
            if (lastView != null) showDetail(lastView, false, true);
        } else {
            boolean needExpand = view.findViewById(R.id.detail).getVisibility() == View.GONE;
            showDetail(view, needExpand, true);
        }
        lastView = view;
        TransitionManager.beginDelayedTransition(recyclerView, transition);
    }

    public void clickFinish(View v, Homework homework) {
        boolean finished = homework.getFinished();
        v.findViewById(R.id.icon).startAnimation(finished ? fadeOut : fadeIn);
        if (((View) v.getParent().getParent()).findViewById(R.id.detail).getVisibility() == View.VISIBLE)
            showDetail(((View) v.getParent().getParent()), false, false);
        ((View) v.getParent()).findViewById(R.id.deleteLine).startAnimation(finished ? fadeOut : fadeIn);
        ((View) v.getParent()).findViewById(R.id.circle2).startAnimation(finished ? fadeOut : fadeIn);
        View text = ((View) v.getParent()).findViewById(R.id.text);
        text.startAnimation(finished ? alphaIn : alphaOut);
        homework.setFinished(!finished);
        MainViewModel.getInstance().appendHomework(homework);
    }

    private void showDetail(View view, boolean visible, boolean needAnim) {
        View detail = view.findViewById(R.id.detail);
        View text = view.findViewById(R.id.text2);
        int status = visible ? View.VISIBLE : View.GONE;
        if (status != detail.getVisibility()) {
            detail.setVisibility(visible ? View.VISIBLE : View.GONE);
            text.setVisibility(visible ? View.VISIBLE : View.GONE);
            if (needAnim) {
                text.startAnimation(visible ? fadeIn : fadeOut);
                detail.startAnimation(visible ? fadeIn : fadeOut);
            }
        }
    }

    public void setPlan(Homework homework) {
        this.homework = homework;
        Calendar deadline = Calendar.getInstance();
        if (homework.planDate != null)
            deadline.setTime(homework.planDate);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                deadline.get(Calendar.YEAR),
                deadline.get(Calendar.MONTH),
                deadline.get(Calendar.DAY_OF_MONTH)
        );
        deadline.setTime(new Date());
        dpd.setMinDate(deadline);
        dpd.vibrate(false);// 禁止震动
        dpd.show(((Activity) mContext).getFragmentManager(), null);
    }

    public void showDetail(Homework homework) {
        CommentFragment.newInstance(homework).show(((AppCompatActivity) mContext).getSupportFragmentManager(), null);
    }

    public void showImg(Homework homework) {
        View view = View.inflate(mContext, R.layout.dialog_img, null);
        if (homework.imgUri != null)
            Glide.with(mContext).load(homework.getImgUri()).into((ImageView) view.findViewById(R.id.image));
        else {
            view.findViewById(R.id.add).setOnClickListener((v) -> {

            });
        }
        new AlertDialog.Builder(mContext)
                .setTitle(homework.getTitle())
                .setView(view)
                .setPositiveButton("确定", null)
                .show();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        homework.setPlanDate(new Date(year - 1900, monthOfYear, dayOfMonth));
        Log.d(TAG, "onDateSet: year: " + year + " month: " + monthOfYear + " day: " + dayOfMonth);
        viewModel.appendHomework(homework);
    }
}
