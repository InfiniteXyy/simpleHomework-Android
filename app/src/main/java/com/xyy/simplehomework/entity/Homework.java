package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.view.adapter.WeekAdapter;
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
    private static SimpleDateFormat initDateFormat;
    @Id
    public long id;
    public ToOne<MyProject> project;
    public String title;
    public String detail;
    public Date deadline;
    public Date planDate;
    public Date initDate;
    public int status;

    public Homework(String title) {
        this.title = title;
        initDate = DateHelper.date;
    }

    public Homework() {
    }

    @Override
    public int getItemType() {
        return WeekAdapter.TYPE_HOMEWORK;
    }
}
