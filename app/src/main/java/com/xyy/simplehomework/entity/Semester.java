package com.xyy.simplehomework.entity;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by xyy on 2018/1/29.
 */

@Entity
public class Semester {
    public static final int FIRST_TERM = 0;
    public static final int SECOND_TERM = 1;
    @Id
    public long id;
    @Backlink
    public ToMany<MySubject> allSubjects;

    public Date startDate;
    public Date endDate;
    public int grade; // 大一是12年级
    public int term;

    public Semester() {
    }

    public Semester(int grade, int term) {
        this.grade = grade;
        this.term = term;
    }
}
