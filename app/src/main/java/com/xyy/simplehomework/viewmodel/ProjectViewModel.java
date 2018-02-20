package com.xyy.simplehomework.viewmodel;

import android.content.Context;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Homework_;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.Semester;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.model.DataServer;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.fragments.PageFragment;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by xyy on 2018/2/5.
 */

public class ProjectViewModel implements OnDateSetListener {
    private static ProjectViewModel instance;
    private DataServer dataServer;
    private Context mContext;
    private BoxStore boxStore;
    private Homework onChangingHomework;
    private Map<PageFragment, List<Homework>> dayMap;

    public ProjectViewModel(Context context) {
        mContext = context;
        instance = this;
        // set up data server
        boxStore = ((App) context.getApplicationContext()).getBoxStore();

        dataServer = new DataServer(boxStore);
        dataServer.resetAll();

        // get week and semester
        Semester semester = getThisSemester();
        Week week = getThisWeek(semester);
        DateHelper.setUp(week, semester);

        dayMap = new HashMap<>();
        useDemo(week, semester);
    }

    public static ProjectViewModel getInstance() {
        return instance;
    }


    public List<Homework> getHomework(PageFragment pf) {
        if (dayMap.get(pf) == null) {
            List<Homework> homeworkList = new ArrayList<>();
            Query<Homework> query = boxStore.boxFor(Homework.class).query().equal(Homework_.planDate, pf.getDate()).build();
            homeworkList.addAll(query.find());
            dayMap.put(pf, homeworkList);
        }
        return dayMap.get(pf);
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
            subject.availableWeeks = new byte[]{6,7,8,9};
            thisSemester.allSubjects.add(subject);
        }
        thisSemester.allSubjects.applyChangesToDb();

        // refresh this week projects
        week.homeworks.reset();

        // homework demo
        int i = 0;
        for (MySubject subject : subjects) {
            if (i < 3) {
                Homework homework = new Homework(subject.getName() + "练习" + (i + 1), DateHelper.afterDays(i + 2));
                homework.week.setTarget(week);
                dataServer.put(homework);
            }
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

    public void finishHomework() {
        onChangingHomework.setFinished();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        onChangingHomework.setPlanDate(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
        dataServer.put(onChangingHomework);
        updateDayView();
    }

    private void updateDayView() {
        for (PageFragment fragment : dayMap.keySet()) {
            List<Homework> temp = boxStore.boxFor(Homework.class).query().equal(Homework_.planDate, fragment.getDate()).build().find();
            fragment.updateAdapter(temp);
        }
    }
}
