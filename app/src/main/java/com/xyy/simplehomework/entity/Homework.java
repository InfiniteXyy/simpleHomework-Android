package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.view.adapter.SmallProjectAdapter;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/2/8.
 */
@Entity
public class Homework extends BaseObservable implements MultiItemEntity {
    public static final int HAS_FINISHED = 0;
    public static final int TOBE_DONE = 1;
    @Id
    public long id;
    public ToOne<MyProject> project;
    public String detail;
    public Date deadline;
    public Date planDate;
    public int status;

    public Homework(String detail) {
        this.detail = detail;
        planDate = DateHelper.date;
    }

    public Homework() {
    }

    @Bindable
    public String getStatus() {
        return Integer.toString(status);
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setPlanDate(Date date) {
        planDate = date;
    }


    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_HOMEWORK;
    }
}
