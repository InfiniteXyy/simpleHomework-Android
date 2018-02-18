package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
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
    public static final int FINISHED = 0;
    public static final int NOT_FINISHED = 1;
    @Id
    public long id;
    public ToOne<MyProject> project;
    public String title;
    public String detail;
    public Date deadline;
    public Date planDate;
    public Date initDate;
    public int status;

    public Homework(String title, Date deadline) {
        this.title = title;
        this.deadline = deadline;
        initDate = DateHelper.date;
        status = NOT_FINISHED;
    }

    public Homework() {
    }

    public void setFinished() {
        status = FINISHED;
    }

    public boolean hasFinished() {
        return status == FINISHED;
    }

    @Bindable
    public String getPlanDate() {
        if (planDate == null) return null;
        else
            return Integer.toString(DateHelper.getTimeBetween(DateHelper.date, planDate, DateHelper.DAY))
                    + "d after";
    }

    public void setPlanDate(Date date) {
        planDate = date;
        notifyPropertyChanged(BR.planDate);
    }

    public String getDeadline() {
        return new SimpleDateFormat("M.d").format(deadline);
    }

    @Override
    public int getItemType() {
        return WeekAdapter.TYPE_HOMEWORK;
    }
}
