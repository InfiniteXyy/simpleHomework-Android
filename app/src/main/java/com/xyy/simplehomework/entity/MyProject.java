package com.xyy.simplehomework.entity;

import android.databinding.BindingAdapter;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.adapter.SmallProjectAdapter;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject extends AbstractExpandableItem<Homework> implements MultiItemEntity {
    @Id
    public long id;

    public ToOne<MySubject> subject;
    public ToOne<Week> week;
    @Backlink
    public ToMany<Homework> homework;

    public MyProject() {

    }

    @BindingAdapter("cardColor")
    public static void setCardColor(View view, MyProject project) {
        CardView cv = (CardView) view;
        if (project.homework.isEmpty()) {
            cv.setCardBackgroundColor(view.getResources().getColor(R.color.japanWhite));
        } else {
            cv.setCardBackgroundColor(view.getResources().getColor(project.subject.getTarget().colorId));
        }
    }

    @BindingAdapter("cardElevation")
    public static void setCardElevation(View view, MyProject project) {
        CardView cv = (CardView) view;
        if (project.homework.isEmpty()) {
            cv.setCardElevation(0.0f);
        } else {
            cv.setCardElevation(8.0f);
        }
    }

    @Override
    public int getItemType() {
        return SmallProjectAdapter.TYPE_PROJECT;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
