package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.xyy.simplehomework.BR;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/19.
 */

@Entity
public class MySubject extends BaseObservable{
    @Backlink
    public ToMany<MyProject> projects;
    public ToOne<Semester> semester;

    public String name;
    public int colorId;
    public byte[] availableWeeks;
    @Id
    long id;

    public MySubject(String name, int colorId) {
        this.name = name;
        this.colorId = colorId;
    }

    public MySubject() {
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}

