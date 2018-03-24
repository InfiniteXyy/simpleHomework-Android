package com.xyy.simplehomework.entity;

import android.view.ViewAnimationUtils;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by xyy on 2018/3/24.
 */

public class Article {

    // 用来生成随机标题
    private static final Random random = new Random();
    private static final String[] titles = {
            "Stack Overflow 2018 开发者调查报告出炉",
            "阿里资深Java工程师表述架构的腐化之谜",
            "H5主流浏览器下App导流方案选取",
            "小程序直接分享朋友圈还有多远？"
    };
    private static final int[] images = {
            R.drawable.stackoverflow,
            R.drawable.java,
            R.drawable.wechat
    };
    private static int num = 0;
    private String title;
    private Date time;
    private int imgRes;


    public Article() {
        title = titles[num % 4];
        imgRes = images[num % 3];
        time = DateHelper.afterDays(random.nextInt(5));
        num++;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy年M月d日", Locale.CHINA).format(time);
    }

    public int getImgRes() {
        return imgRes;
    }
}
