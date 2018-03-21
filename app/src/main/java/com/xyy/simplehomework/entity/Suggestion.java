package com.xyy.simplehomework.entity;

/**
 * Created by xyy on 2018/3/21.
 */

public class Suggestion {

    private String title;

    private int imgRes;

    public Suggestion(String title, int imgRes) {
        this.title = title;
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
