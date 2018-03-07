package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.freelib.multiitem.item.ItemData;
import com.freelib.multiitem.item.ItemDrag;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/2/8.
 */
@Entity
public class Homework extends BaseObservable implements ItemDrag, ItemData{
    public static final int FINISHED = 0;
    public static final int NOT_FINISHED = 1;
    private final static DateFormat FORMAT = SimpleDateFormat.getDateInstance();
    @Id
    public long id;
    public int weekIndex;
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
        initDate = new Date();
        status = NOT_FINISHED;
    }


    @Bindable
    public String getPlanDate() {
        if (planDate == null) return null;
        else return DateHelper.afterDayFormat(planDate);
    }

    public void setPlanDate(Date date) {
        planDate = date;
        notifyPropertyChanged(BR.planDate);
    }

    @Bindable
    public String getDeadline() {
        return DateHelper.afterDayFormat(deadline);
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

    @Override
    public boolean isCanMove() {
        return true;
    }

    @Override
    public boolean isCanChangeRecycler() {
        return true;
    }

    @Override
    public boolean isCanDrag() {
        return true;
    }

    @Override
    public void setVisibility(int i) {

    }

    @Override
    public int getVisibility() {
        return 0;
    }
}
