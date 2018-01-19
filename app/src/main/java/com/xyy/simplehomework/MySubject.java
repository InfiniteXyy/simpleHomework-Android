package com.xyy.simplehomework;

/**
 * Created by xyy on 2018/1/19.
 */

public class MySubject {
    private String name;

    private int imgId;

    public MySubject(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}

