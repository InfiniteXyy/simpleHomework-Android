package com.xyy.simplehomework.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * An entity for comments behind each homework
 */

@Entity
public class Note {
    @Id
    public long id;
    public ToOne<Homework> homework;
    public String comment;
}
