package com.xyy.simplehomework.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.adapter.SmallProjectAdapter;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject implements MultiItemEntity {
    public String detail;
    public Date deadline;
    public Date initDate;
    public Date myDate;
    public ToOne<MySubject> subject;
    public ToOne<Week> week;
    public int score;
    public int status;
    public boolean hasSetDate = false;
    @Id
    long id;

    public MyProject() {
        status = SmallProjectAdapter.TYPE_PROJECT_RECORD;
        this.deadline = new Date();
    }

    public void recordHomework(String detail, Date deadline) {
        this.detail = detail;
        this.initDate = new Date();
        this.deadline = deadline;
        status = SmallProjectAdapter.TYPE_PROJECT_TOBE;
    }

    public void setMyOwnDate(Date date) {
        myDate = date;
        hasSetDate = true;
    }

    @Override
    public int getItemType() {
        return status;
    }
}
