package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.view.adapter.SmallProjectAdapter;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.text.SimpleDateFormat;
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

    public void setPlanDate(Date date) {
        planDate = date;
    }

    public String getDeadlineFormat() {
        if (planDate.compareTo(DateHelper.date) == 0) {
            return new SimpleDateFormat("截止时间 M.d 状态：" + status).format(DateHelper.date);
        } else {
            return new SimpleDateFormat("计划时间 M.d 状态：" + status).format(planDate);
        }
    }


    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_HOMEWORK;
    }
}
