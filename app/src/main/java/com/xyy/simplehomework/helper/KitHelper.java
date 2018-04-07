package com.xyy.simplehomework.helper;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.xyy.simplehomework.view.App;

import java.lang.reflect.Field;

/**
 * A Util for all kinds of kit customization
 */

public class KitHelper {
    /**
     * make tab underline a little bit shorter
     *
     * @param tabLayout for dependency injection
     */
    public static void setUpIndicatorWidth(TabLayout tabLayout) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(App.dp2px(18f));
                    params.setMarginEnd(App.dp2px(18f));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
