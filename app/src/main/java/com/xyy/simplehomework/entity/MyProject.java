package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.adapter.SmallProjectAdapter;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject extends BaseObservable implements MultiItemEntity {
    public static final int TYPE_PROJECT_FIN = 0;
    public static final int TYPE_PROJECT_TOBE = 1;
    public static final int TYPE_PROJECT_RECORD = 2;

    public String detail;
    public String subjectName;
    public Date deadline;
    public Date myDate;
    public ToOne<MySubject> subject;
    public ToOne<Week> week;
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
    @BindingAdapter("cardElevation")
    public static void setCardElevation(View view, int status) {
        CardView cv = (CardView) view;
        cv.setCardElevation(status == TYPE_PROJECT_TOBE ? 8.0f : 0.0f);
    }

    @BindingAdapter("cardBackgroundColor")
    public static void setCardBackgroundColor(View view, MyProject project) {
        CardView cv = (CardView) view;
        if (project.status == TYPE_PROJECT_TOBE) {
            cv.setCardBackgroundColor(view.getResources().getColor(project.colorId));
        } else {
            cv.setCardBackgroundColor(view.getResources().getColor(R.color.japanWhite));
        }
    }
    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_PROJECT;
    }
}
