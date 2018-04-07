package com.xyy.simplehomework.entity;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * An Entity for certain semester
 */

@Entity
public class Semester {
    @Id
    public long id;
    @Backlink
    public ToMany<MySubject> allSubjects;

    public Date startDate;
    public Date endDate;

    public Semester() {
    }
}
