package com.xyy.simplehomework.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.view.adapter.SmallProjectAdapter;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/2/8.
 */
@Entity
public class Homework implements MultiItemEntity {
    @Id
    public long id;

    public static final int HAS_FINISHED = 0;
    public static final int TOBE_DONE = 1;

    public ToOne<MyProject> project;
    public String detail;
    public Date deadline;
    public int status;

    public Homework(String detail) {
        this.detail = detail;
    }

    public Homework() {
    }

    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_HOMEWORK;
    }
}
