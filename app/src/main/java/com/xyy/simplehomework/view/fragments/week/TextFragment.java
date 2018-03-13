package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/3.
 */

public class TextFragment extends Fragment {
    private BaseQuickAdapter<WeekSubject, BaseViewHolder> adapter;
    private List<WeekSubject> weekSubjects;
    private List<MySubject> subjects;

    public static TextFragment newInstance(List<MySubject> data, int weekIndex) {
        TextFragment textFragment = new TextFragment();
        textFragment.subjects = data;
        textFragment.setWeekSubjects(textFragment.getWeekSubjectList(weekIndex));
        return textFragment;
    }

    public void setWeekSubjects(List<WeekSubject> weekSubjects) {
        this.weekSubjects = weekSubjects;
    }

    private List<WeekSubject> getWeekSubjectList(int weekIndex) {
        List<WeekSubject> subjects = new ArrayList<>();
        for (MySubject subject : this.subjects) {
            List<Homework> temp = new ArrayList<>();
            for (Homework homework : subject.homework) {
                if (homework.weekIndex == weekIndex) {
                    temp.add(homework);
                }
            }
            WeekSubject weekSubject = new WeekSubject(subject);
            weekSubject.setHomeworkThisWeek(temp);
            subjects.add(weekSubject);
        }
        return subjects;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new BaseQuickAdapter<WeekSubject, BaseViewHolder>(R.layout.item_text_subject, weekSubjects) {
            @Override
            protected void convert(BaseViewHolder helper, WeekSubject item) {
                helper.setText(R.id.subject_name, item.getSubject().getName());
                RecyclerView homeworkRecycler = helper.getView(R.id.recycler_view);
                homeworkRecycler.setAdapter(new BaseQuickAdapter<Homework, BaseViewHolder>(R.layout.item_text_homework, item.homeworkThisWeek) {
                    @Override
                    protected void convert(BaseViewHolder helper, Homework item) {
                        helper.setText(R.id.homework, item.getTitle());
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }


    void updateHomeworkList() {
        for (WeekSubject subject : weekSubjects) {
            subject.getSubject().homework.reset();
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void setWeek(int week) {
        adapter.replaceData(getWeekSubjectList(week));
    }

    public static class WeekSubject {
        private MySubject subject;
        private List<Homework> homeworkThisWeek;

        public WeekSubject(MySubject subject) {
            this.subject = subject;
        }

        public MySubject getSubject() {
            return subject;
        }

        public List<Homework> getHomeworkThisWeek() {
            return homeworkThisWeek;
        }

        public void setHomeworkThisWeek(List<Homework> homeworkThisWeek) {
            this.homeworkThisWeek = homeworkThisWeek;
        }
    }
}
