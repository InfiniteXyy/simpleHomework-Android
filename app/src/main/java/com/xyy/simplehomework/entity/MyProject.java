package com.xyy.simplehomework.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.view.adapter.WeekAdapter;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject implements MultiItemEntity {
    @Id
    public long id;

    public ToOne<MySubject> subject;
    public ToOne<Week> week;
    @Backlink
    public ToMany<Homework> homework;

    public MyProject() {

    }

    @Override
    public int getItemType() {
        return WeekAdapter.TYPE_PROJECT;
    }
}
