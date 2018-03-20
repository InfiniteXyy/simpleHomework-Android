package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.home.FragmentPlan;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/2/8.
 */
@Entity
public class Homework extends BaseObservable implements MultiItemEntity {
    public static final int WRITING = 0;
    public static final int COMPUTER = 1;
    public static final int LISTENING = 2;
    public static final int READDING = 3;
    private final static DateFormat FORMAT = SimpleDateFormat.getDateInstance();
    @Id
    public long id;
    public int weekIndex;
    public ToOne<MySubject> subject;
    public ToMany<Note> notes;

    public String title;
    public String detail;

    public Date deadline;
    public Date planDate;
    public Date initDate;
    public String imgUri;
    public boolean finished;
    public int type;

    public Homework(String title, Date deadline) {
        this.title = title;
        this.deadline = deadline;
        initDate = DateHelper.date;
        finished = false;
    }

    public Homework() {
        initDate = new Date();
        finished = false;
    }

    @BindingAdapter("android:typeIc")
    public static void setIc(View view, int type) {
        ImageView imageView = (ImageView) view;
        int imgSrc = 0;
        switch (type) {
            case WRITING:
                imgSrc = R.drawable.ic_writing_black_24px;
                break;
            case LISTENING:
                imgSrc = R.drawable.ic_listeningwork_black_24px;
                break;
            case COMPUTER:
                imgSrc = R.drawable.ic_computerwork_black_24px;
                break;
        }
        imageView.setImageResource(imgSrc);
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
        notifyPropertyChanged(BR.deadlineFormal);
    }

    @Bindable
    public String getDeadlineFormal() {
        return FORMAT.format(deadline);
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

    @Bindable
    public int getNoteSize() {
        return notes.size();
    }

    @Bindable
    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        notifyPropertyChanged(BR.finished);
    }


    @Bindable
    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
        notifyPropertyChanged(BR.imgUri);
    }

    @Override
    public int getItemType() {
        return FragmentPlan.TYPE_PLAN;
    }

}
