package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.model.DataServer;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectViewModel implements OnDateSetListener{
    private DataServer dataServer;
    private Context mContext;
    private List<MyProject> projectList;
    private static ProjectViewModel instance;
    private Homework onChangingHomework;

    public static ProjectViewModel getInstance() {
        return instance;
    }
    public ProjectViewModel(Context context) {
        mContext = context;
        instance = this;
        // set up data server
        BoxStore boxStore = ((App) context.getApplicationContext()).getBoxStore();

        dataServer = new DataServer(boxStore);
        dataServer.resetAll();

        // get week and semester
        Semester semester = getThisSemester();
        Week week = getThisWeek(semester);
        DateHelper.setUp(week, semester);

        useDemo(week, semester);
        projectList = new ArrayList<>();
    }

    public List<MyProject> getProjectsThisWeek() {
        projectList = dataServer.getAllProjects();
        return projectList;
    }

    public List<Homework> getAllHomework() {
        return dataServer.getAllHomework();
    }

    private void useDemo(Week week, Semester semester) {
        Semester thisSemester = getThisSemester();

        MySubject[] subjects = {
                new MySubject("计算机系统", mContext.getResources().getColor(R.color.japanBrown)),
                new MySubject("高等数学", mContext.getResources().getColor(R.color.japanBlue)),
                new MySubject("线性代数", mContext.getResources().getColor(R.color.japanPink)),
                new MySubject("离散数学", mContext.getResources().getColor(R.color.japanTea)),
                new MySubject("概率论", mContext.getResources().getColor(R.color.japanOrange)),
        };

        for (MySubject subject : subjects) {
            subject.semester.setTarget(thisSemester);
            subject.availableWeeks = new byte[]{4, 5, 6};
            thisSemester.allSubjects.add(subject);
        }
        thisSemester.allSubjects.applyChangesToDb();

        // refresh this week projects
        week.projects.reset();
        for (MySubject subject : semester.allSubjects) {
            for (byte aWeek : subject.availableWeeks)
                if (week.weekIndex == aWeek) {
                    MyProject project = new MyProject();
                    project.subject.setTarget(subject);
                    week.projects.add(project);
                    break;
                }
        }

        // homework demo
        int i = 0;
        for (MyProject project : week.projects) {
            if (i < 3) {
                Homework homework = new Homework(project.subject.getTarget().getName() + "练习" + (i + 1), DateHelper.afterDays(i+2));
                project.homework.add(homework);
                project.week.setTarget(week);
            }
            dataServer.put(project);
            i++;
        }
    }

    private Week getThisWeek(Semester semester) {
        int weekIndex = DateHelper.getTimeBetween(semester.startDate, new Date(), DateHelper.WEEK);
        for (Week week : semester.weeks) {
            if (week.weekIndex == weekIndex) {
                return week;
            }
        }
        Week week = new Week();
        week.weekIndex = weekIndex;
        week.semester.setTarget(semester);
        dataServer.put(week);
        return week;
    }

    private Semester getThisSemester() {
        Semester semester = dataServer.findSemester();
        if (semester == null) {
            semester = new Semester(12, Semester.FIRST_TERM);
            semester.startDate = new Date(118, 0, 1);
            semester.endDate = new Date(118, 10, 2);
            dataServer.put(semester);
        }
        return semester;
    }


    public void setCurrentHomework(Homework homework) {
        onChangingHomework = homework;
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        onChangingHomework.setPlanDate(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
        dataServer.put(onChangingHomework);
    }
}
