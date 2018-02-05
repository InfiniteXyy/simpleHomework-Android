package com.xyy.simplehomework.data;

import android.util.Log;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Semester_;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.fragments.DayFragment;
import com.xyy.simplehomework.fragments.SemesterFragment;
import com.xyy.simplehomework.fragments.WeekFragment;
import com.xyy.simplehomework.helper.DateHelper;
import com.xyy.simplehomework.helper.TitleSwitcher;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/2.
 */

public class DataServer {
    private String TAG = "DataServer";
    private Box<MySubject> subjectBox;
    private Box<MyProject> projectBox;
    private Box<Semester> semesterBox;
    private Box<Week> weekBox;

    private Week thisWeek;

    public DataServer(BoxStore boxStore) {
        subjectBox = boxStore.boxFor(MySubject.class);
        projectBox = boxStore.boxFor(MyProject.class);
        semesterBox = boxStore.boxFor(Semester.class);
        weekBox = boxStore.boxFor(Week.class);
    }

    public void bindToViews(DayFragment dayFragment, WeekFragment weekFragment, SemesterFragment semesterFragment, TitleSwitcher switcher) {
        dayFragment.updateDailyProjects(projectBox.getAll());
        weekFragment.updateWeekList(thisWeek);
        switcher.setUpSwitcher();
    }

    private void refreshWeekProjects(Week week, Semester semester) {
        week.projects.reset();
        for (MySubject subject : semester.allSubjects) {
            for (byte aWeek : subject.availableWeeks)
                if (week.weekIndex == aWeek) {
                    Log.d(TAG, "put new subject: " + subject.name);
                    MyProject project = new MyProject();
                    project.subject.setTarget(subject);
                    week.projects.add(project);
                    break;
                }
        }
    }

    private Week getThisWeek(Semester semester) {
        int weekIndex = DateHelper.getWeeksBetween(semester.startDate, new Date());
        for (Week week : semester.weeks) {
            if (week.weekIndex == weekIndex) {
                return week;
            }
        }
        Log.d(TAG, "getThisWeek: week not exist, set new week");
        Week week = new Week();
        week.weekIndex = weekIndex;
        week.semester.setTarget(semester);
        refreshWeekProjects(week, semester);
        weekBox.put(week);
        return week;
    }

    private Semester getThisSemester() {
        Date date = DateHelper.date;
        Semester semester = semesterBox.query()
                .greater(Semester_.endDate, date)
                .less(Semester_.startDate, date)
                .build().findFirst();
        if (semester == null) {
            Log.d(TAG, "getThisSemester: semester not exist, set new semester");
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 0, 1);
            semester.endDate = new Date(118, 10, 2);
            semesterBox.put(semester);
        }
        return semester;
    }


    public void bindToDateHelper() {
        DateHelper.setUp(thisWeek, getThisSemester());
    }

    public void useDemo() {
        weekBox.removeAll();
        semesterBox.removeAll();
        subjectBox.removeAll();
        projectBox.removeAll();

        Semester thisSemester = getThisSemester();
        thisWeek = getThisWeek(thisSemester);

        MySubject[] subjects = {
                new MySubject("计算机系统", R.color.japanBrown),
                new MySubject("高等数学", R.color.japanBlue),
                new MySubject("线性代数", R.color.japanPink),
                new MySubject("离散数学", R.color.japanTea),
                new MySubject("概率论", R.color.japanOrange),
        };
        for (MySubject subject : subjects) {
            subject.semester.setTarget(thisSemester);
            subject.availableWeeks = new byte[]{4, 5, 6};
            thisSemester.allSubjects.add(subject);
        }
        thisSemester.allSubjects.applyChangesToDb();

        refreshWeekProjects(thisWeek, thisSemester);

        int i = 0;
        for (MyProject project : thisWeek.projects) {
            if (i <= 2) {
                project.recordHomework("完成" + i + "页", new Date());
            }
            project.setSubject(subjects[i]);
            projectBox.put(project);
            i++;
        }
    }
}
