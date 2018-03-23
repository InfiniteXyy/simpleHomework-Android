package com.xyy.simplehomework.view.fragments.week;

import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/3/5.
 */

public class Week {
    public int weekIndex;
    private List<Homework> homeworkList;
    private boolean finished = true;
    private int num = 0;
    private Date latestDate;

    public Week(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public int getProgress() {
        int allSize = homeworkList.size();
        return (int)(((float)(allSize - num)/allSize) * 100);
    }

    public boolean hasFinished() {
        return finished;
    }


    public String getWeekName() {
        return "第" + DateHelper.num2cn[weekIndex + 1] + "周";
    }

    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
        Collections.sort(homeworkList, ((o1, o2) -> o1.getFinished() ? -1 : 1));
        num = 0;
        latestDate = new Date(0);
        for (Homework homework : homeworkList) {
            if (!homework.getFinished()) {
                finished = false;
                num++;
                if (latestDate.before(homework.deadline)) latestDate = homework.deadline;
            }
        }
    }

    public String getLatestDue() {
        return "最近的截止日期：" + DateHelper.afterDayFormat(latestDate);
    }
}
