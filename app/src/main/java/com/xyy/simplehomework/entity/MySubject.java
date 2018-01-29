package com.xyy.simplehomework.entity;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/19.
 */

@Entity
public class MySubject {
    public final static int HAS_FINISHED = 0;
    public final static int TOBE_DONE = 1;
    public final static int TOBE_RECORD = 2;

    @Backlink
    public ToMany<MyProject> projects;
    public ToOne<Semester> semester;

    public int status; // for test

    public String name;
    public int imgId;
    public int colorId;
    public int weeks;
    @Id
    long id;

    public MySubject(String name, int imgId, int colorId) {
        this.name = name;
        this.imgId = imgId;
        this.colorId = colorId;
        status = TOBE_DONE;
    }

    public MySubject() {
    }
}

