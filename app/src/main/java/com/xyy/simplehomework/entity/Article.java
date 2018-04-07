package com.xyy.simplehomework.entity;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.helper.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * A domain for Index Cards
 * Capture articles from website like <a href='www.juejin.com'>掘金 etc</a>
 */

public class Article {

    private static final Random random = new Random();

    // demo article set
    private static final Map<String, Integer> data = new HashMap<String, Integer>() {
        {
            put("Stack Overflow 2018 开发者调查报告出炉", R.drawable.stackoverflow);
            put("阿里资深Java工程师表述架构的腐化之谜", R.drawable.java);
            put("小程序直接分享朋友圈还有多远？", R.drawable.wechat);
        }
    };

    private static final Iterator<Map.Entry<String, Integer>> iterator = data.entrySet().iterator();
    private String title;
    private int imgRes;
    private Date time;


    public Article() {
        Map.Entry<String, Integer> entry = iterator.next();
        title = entry.getKey();
        imgRes = entry.getValue();
        time = DateHelper.afterDays(random.nextInt(5));
    }

    public static void main(String[] args) {
        Article a = new Article();
        Article b = new Article();
        System.out.println(a);
        System.out.println(b);
    }

    public String getTitle() {
        return title;
    }

    /**
     * get released date
     *
     * @return well-formatted date string <em>2018年12月2日</em>
     */
    public String getTime() {
        return new SimpleDateFormat("yyyy年M月d日", Locale.CHINA).format(time);
    }

    public int getImgRes() {
        return imgRes;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", imgRes=" + imgRes +
                ", time=" + time +
                '}';
    }

}
