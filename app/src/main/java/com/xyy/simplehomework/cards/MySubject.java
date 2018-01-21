package com.xyy.simplehomework.cards;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by xyy on 2018/1/19.
 */

@Entity
public class MySubject {

    @Id
    long id;

    String name;
    int imgId;

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

