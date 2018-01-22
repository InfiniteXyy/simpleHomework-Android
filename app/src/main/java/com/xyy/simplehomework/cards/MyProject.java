package com.xyy.simplehomework.cards;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject {
    @Id
    long id;

    public String book;
    public Date deadline;
    public Date initDate;
    public ToOne<MySubject> subject;
    int score;

    public MyProject(String book) {
        this.book = book;
    }

    public MyProject() {
    }
}