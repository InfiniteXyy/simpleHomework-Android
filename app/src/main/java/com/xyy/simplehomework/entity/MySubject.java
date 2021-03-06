package com.xyy.simplehomework.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.view.fragments.home.FragmentSubjects;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * An entity for Subjects including (Math, Sports, or even breakfast)
 */

@Entity
public class MySubject extends BaseObservable implements MultiItemEntity {
    @Id
    public long id;

    @Backlink
    public ToMany<Homework> homework;
    public ToOne<Semester> semester;


    public String name;

    public MySubject(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getItemType() {
        return FragmentSubjects.TYPE_SUBJECT;
    }
}

