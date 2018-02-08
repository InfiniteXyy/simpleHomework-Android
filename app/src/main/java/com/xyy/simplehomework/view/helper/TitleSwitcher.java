package com.xyy.simplehomework.view.helper;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.App;

/**
 * Created by xyy on 2018/1/26.
 */

public class TitleSwitcher {
    public final static int DAY = 0;
    public final static int WEEK = 1;
    public final static int SEMESTER = 2;

    private Typeface typeface;
    private Context context;
    private TextSwitcher dayName;
    private int old_position;
    private int old_position_title;

    public TitleSwitcher(Context mContext) {
        this.context = mContext;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
        initSwitcher();
    }

    @BindingConversion
    public static Typeface convertStringToFace(String s) {
        try {
            return Typeface.createFromAsset(App.getInstance().getAssets(), s);
        } catch (Exception e) {
            throw e;
        }
    }

    private void initSwitcher() {
        dayName = ((Activity) context).findViewById(R.id.day_name);
        dayName.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                tv.setTextAppearance(context, R.style.dayNameSwitcher);
                tv.setTypeface(typeface, Typeface.BOLD);
                return tv;
            }
        });

        dayName.setCurrentText(DateHelper.getDayName().toUpperCase());
        old_position = DateHelper.getDayIndex();
        old_position_title = DAY;
    }

    public void setAlpha(float alpha) {
        dayName.setAlpha(alpha);
    }

    public void setText(int dayIndex) {
        if (old_position < dayIndex) {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
        } else {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
        }
        old_position = dayIndex;
        dayName.setText(DateHelper.weeks[dayIndex].toUpperCase());

    }

    public void changeFragmentTitle(int position) {
        if (old_position_title < position) {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_top));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom));
        } else {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_top));
        }
        old_position_title = position;
        switch (position) {
            case WEEK:
                dayName.setText("WEEK " + DateHelper.getWeekIndex());
                break;
            case DAY:
                dayName.setText(DateHelper.weeks[old_position].toUpperCase());
                break;
            case SEMESTER:
                dayName.setText(DateHelper.getSemesterName());
                break;
            default:
                break;
        }
    }
}
