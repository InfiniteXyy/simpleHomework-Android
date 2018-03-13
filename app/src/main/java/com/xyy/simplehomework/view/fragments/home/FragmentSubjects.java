package com.xyy.simplehomework.view.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;
import com.xyy.simplehomework.view.helper.SimpleDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentSubjects extends Fragment {
    private RecyclerView recyclerView;
    private List<MySubject> subjectList;

    public static FragmentSubjects newInstance(List<MySubject> subjects) {
        FragmentSubjects fragmentSubjects = new FragmentSubjects();
        fragmentSubjects.setSubjectList(subjects);
        return fragmentSubjects;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setAdapter(new BaseQuickAdapter<MySubject, BaseViewHolder>(R.layout.item_home_subject,
                subjectList) {
            @Override
            protected void convert(BaseViewHolder helper, MySubject item) {
                helper.setText(R.id.title, item.getName());
                // TODO: 怎么自动判断status = 未完成 的数目
                helper.setText(R.id.textView, item.homework.size()+"个项目未完成");
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), 1));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
    }

    public void setSubjectList(List<MySubject> subjectList) {
        this.subjectList = subjectList;
    }
}
