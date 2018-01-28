package com.xyy.simplehomework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyy on 2018/1/26.
 */

public class DayNameSwitcher {
    private Context context;
    private TextSwitcher dayName;
    private int old_position;
    public final static int WEEK = 0;
    public final static int DAY = 1;

    public DayNameSwitcher(Context mContext) {
        this.context = mContext;
        initSwitcher();
    }

    private void initSwitcher() {
        // 导入字体
        final Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");

        dayName = ((Activity) context).findViewById(R.id.day_name);
        dayName.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(context);
                tv.setTextAppearance(context, R.style.dayNameSwitcher);
                tv.setTypeface(typeFace, Typeface.BOLD);
                return tv;
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        dayName.setCurrentText(MainActivity.weeks[week_index].toUpperCase());
        old_position = 0;
    }

    public void setAlpha(float alpha) {
        dayName.setAlpha(alpha);
    }

    public void setText(int weekIndex) {
        if (old_position < weekIndex) {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
        } else {
            dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
            dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
        }
        old_position = weekIndex;
        dayName.setText(MainActivity.weeks[weekIndex].toUpperCase());
    }

    public void changeFragmentTitle(int type) {
        switch (type) {
            case WEEK:
                dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_top));
                dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom));
                dayName.setText("WEEK 3");
                break;
            case DAY:
                dayName.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
                dayName.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_top));
                dayName.setText("SUNDAY");
        }
    }
}
