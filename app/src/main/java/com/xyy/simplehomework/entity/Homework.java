package com.xyy.simplehomework.entity;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/2/8.
 */
@Entity
public class Homework {
    @Id
    long id;

    public ToOne<MyProject> project;
    public String detail;
    public Date deadline;

    public Homework() {
    }
}
