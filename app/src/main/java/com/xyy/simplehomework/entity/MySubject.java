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
public class MySubject extends BaseObservable {
    @Id
    public long id;

    @Backlink
    public ToMany<Homework> homework;
    public ToOne<Semester> semester;

    public String name;
    public int color;
    public byte[] availableWeeks;


    public MySubject(String name, int color) {
        this.name = name;
        this.color = color;
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
    public String toString() {
        return name;
    }
}

