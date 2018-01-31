package com.xyy.simplehomework.entity;

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
    @Backlink
    public ToMany<MyProject> projects;
    public ToOne<Semester> semester;

    public String name;
    public int colorId;
    public byte[] availableWeeks;
    @Id
    long id;

    public MySubject(String name, int colorId) {
        this.name = name;
        this.colorId = colorId;
    }

    public MySubject() {
    }

}

