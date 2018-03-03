package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.xyy.simplehomework.BR;
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
public class Homework extends BaseObservable {
    private final static SimpleDateFormat format = new SimpleDateFormat("M.d");
    public static final int FINISHED = 0;
    public static final int NOT_FINISHED = 1;
    @Id
    public long id;
    public ToOne<Week> week;
    public ToOne<MySubject> subject;

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
        status = NOT_FINISHED;
    }


    @Bindable
    public String getPlanDate() {
        if (planDate == null) return null;
        else
            // TODO: 用 "今天" "明天" 来代替英文的表达
            return Integer.toString(DateHelper.getTimeBetween(DateHelper.date, planDate, DateHelper.DAY))
                    + "d after";
    }

    public void setPlanDate(Date date) {
        planDate = date;
        notifyPropertyChanged(BR.planDate);
    }

    @Bindable
    public String getDeadline() {
        if (deadline == null) {
            return "";
        }
        return format.format(deadline);
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
        notifyPropertyChanged(BR.deadline);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
