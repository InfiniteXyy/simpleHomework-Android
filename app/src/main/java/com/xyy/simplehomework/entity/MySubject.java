package com.xyy.simplehomework.entity;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by xyy on 2018/1/19.
 */

@Entity
public class MySubject {

    @Backlink
    public ToMany<MyProject> projects;
    public String name;
    public int imgId;
    public int colorId;
    @Id
    long id;


    public MySubject(String name, int imgId, int colorId) {
        this.name = name;
        this.imgId = imgId;
        this.colorId = colorId;
    }

    public MySubject() {
    }
}

