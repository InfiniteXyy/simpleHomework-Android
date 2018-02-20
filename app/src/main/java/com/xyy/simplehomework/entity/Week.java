package com.xyy.simplehomework.entity;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/29.
 */

@Entity
public class Week {
    public int weekIndex;
    public ToOne<Semester> semester;
    @Backlink
    public ToMany<Homework> homeworks;
    @Id
    long id;
}
