package com.xyy.simplehomework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.cards.SmallProjectAdapter;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MyProject_;
import com.xyy.simplehomework.entity.MySubject;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment {
    private SmallProjectAdapter adapter;

    public WeekFragment() {
        adapter = new SmallProjectAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView weekRecyclerView = getActivity().findViewById(R.id.week_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        weekRecyclerView.setLayoutManager(layoutManager);
        weekRecyclerView.setAdapter(adapter);
    }

    public void updateWeekList(List<MySubject> subjects) {
        List<MySubject> fin = new ArrayList<>();
        List<MySubject> tobe = new ArrayList<>();
        List<MySubject> notRecord = new ArrayList<>();

        for (MySubject subject : subjects) {
            for (MyProject project : subject.projects) {
                if (project.week != null) {
                    if (project.status == MyProject.TOBE_DONE) {
                        tobe.add(subject);
                    } else {
                        fin.add(subject);
                    }
                } else {
                    notRecord.add(subject);
                }
            }
        }

        adapter.setHasFinished(fin);
        adapter.setToBeFinished(tobe);
        adapter.setToBeRecorded(notRecord);
    }
}
