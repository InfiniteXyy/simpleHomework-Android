package com.xyy.simplehomework.entity;

/**
 * A domain for index suggestion
 * e.g.(You have a homework due for tomorrow! hurry up.)
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
