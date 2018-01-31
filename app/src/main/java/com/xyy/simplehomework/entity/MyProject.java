package com.xyy.simplehomework.entity;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject {
    public final static int TOBE_DONE = 0;
    public final static int HAS_FINISHED = 1;
    public final static int TOBE_RECORD = 2;

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
        status = TOBE_RECORD;
        this.deadline = new Date();
    }

    public void recordHomework(String detail, Date deadline) {
        this.detail = detail;
        this.initDate = new Date();
        this.deadline = deadline;
        status = TOBE_DONE;
    }

    public void setMyOwnDate(Date date) {
        myDate = date;
        hasSetDate = true;
    }
}
