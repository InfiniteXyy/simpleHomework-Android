package com.xyy.simplehomework.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/3/18.
 */
@Entity
public class Note {
    @Id
    public long id;
    public ToOne<Homework> homework;
    public String comment;
}
