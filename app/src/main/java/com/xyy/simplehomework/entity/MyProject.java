package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject extends BaseObservable implements MultiItemEntity {
    public static final int TYPE_PROJECT_RECORD = 2;
    public static final int TYPE_PROJECT_TOBE = 1;
    public static final int TYPE_PROJECT_FIN = 0;

    public String detail;
    public String subjectName;

    public Date deadline;
    public Date initDate;
    public Date myDate;
    public ToOne<MySubject> subject;
    public ToOne<Week> week;
    public int score;
    public int status;
    public int colorId;
    public boolean hasSetDate = false;
    @Id
    long id;

    public MyProject() {
        status = TYPE_PROJECT_RECORD;
        this.deadline = new Date();
    }

    public void recordHomework(String detail, Date deadline) {
        this.detail = detail;
        this.initDate = new Date();
        this.deadline = deadline;
        status = TYPE_PROJECT_TOBE;
    }

    public void setSubject(MySubject subject) {
        this.subject.setTarget(subject);
        this.colorId = subject.colorId;
        this.subjectName = subject.getName();
    }

    @Bindable
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Override
    public int getItemType() {
        return status;
    }
}
