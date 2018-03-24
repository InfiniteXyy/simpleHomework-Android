package com.xyy.simplehomework.entity;

/**
 * Created by xyy on 2018/3/21.
 */

public class Suggestion {

    private String title;

    private String detail;

    public Suggestion(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDetail() {
        return detail;
    }
}
