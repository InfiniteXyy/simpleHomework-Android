package com.xyy.simplehomework.model;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Semester_;
import com.xyy.simplehomework.entity.Week;

import java.util.Date;
import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/2.
 */

public class DataServer {
    private BoxStore boxStore;

    public DataServer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public void resetAll() {
        boxStore.boxFor(MySubject.class).removeAll();
        boxStore.boxFor(MyProject.class).removeAll();
        boxStore.boxFor(Semester.class).removeAll();
        boxStore.boxFor(Week.class).removeAll();
        boxStore.boxFor(Homework.class).removeAll();
    }

    public List<MyProject> getAllProjects() {
        return boxStore.boxFor(MyProject.class).getAll();
    }

    public List<Homework> getAllHomework() {
        return boxStore.boxFor(Homework.class).getAll();
    }

    public void put(Object object) {
        if (object instanceof Week) {
            boxStore.boxFor(Week.class).put((Week) object);
        } else if (object instanceof Semester) {
            boxStore.boxFor(Semester.class).put((Semester) object);
        } else if (object instanceof MyProject) {
            boxStore.boxFor(MyProject.class).put((MyProject) object);
        } else if (object instanceof MySubject) {
            boxStore.boxFor(MySubject.class).put((MySubject) object);
        } else if (object instanceof Homework) {
            boxStore.boxFor(Homework.class).put((Homework) object);
        }
    }

    public Semester findSemester() {
        return boxStore.boxFor(Semester.class).query()
                .greater(Semester_.endDate, new Date())
                .less(Semester_.startDate, new Date())
                .build().findFirst();
    }
}
