package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
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

    public String getDeadline() {
        if (initDateFormat == null) {
            initDateFormat = new SimpleDateFormat("截止时间: yyyy年M月d日");
        }
        return initDateFormat.format(initDate);
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
