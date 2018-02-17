package com.xyy.simplehomework.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.view.adapter.WeekAdapter;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by xyy on 2018/1/22.
 */

@Entity
public class MyProject extends AbstractExpandableItem<Homework> implements MultiItemEntity {
    public final static int ALL_FIN = 0;
    public final static int NOT_ALL_FIN = 1;
    public final static int NOT_RECORD = 2;

    @Id
    public long id;

    public ToOne<MySubject> subject;
    public ToOne<Week> week;
    @Backlink
    public ToMany<Homework> homework;

    @Transient
    private int status;

    public MyProject() {

    }

    public int getStatus(boolean renew) {
        if (!renew) return status;

        if (homework.isEmpty()) {
            status = NOT_RECORD;
            return NOT_RECORD;
        }
        boolean hasAllFin = true;
        for (Homework homework : homework) {
            if (!homework.hasFinished()) {
                hasAllFin = false;
                break;
            }
        }
        if (hasAllFin) {
            status = ALL_FIN;
            return ALL_FIN;
        } else {
            status = NOT_ALL_FIN;
            return NOT_ALL_FIN;
        }
    }

    @Override
    public int getItemType() {
        return WeekAdapter.TYPE_PROJECT;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
