package com.xyy.simplehomework.view.fragments.subject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;

import java.util.List;

/**
 * Created by xyy on 2018/2/25.
 */

public class SubjectHomeworkFragment extends Fragment {
    private View header;
    private List<Homework> homeworkList;
    private BaseQuickAdapter<Homework, BaseViewHolder> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        header = inflater.inflate(R.layout.item_subject_header, container, false);
        return inflater.inflate(R.layout.fragment_subject_homework, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<Homework, BaseViewHolder>(R.layout.item_subject_homework, homeworkList) {
            @Override
            protected void convert(BaseViewHolder helper, Homework item) {
                helper.setText(R.id.homework, item.getTitle());
            }
        };
        adapter.setHeaderView(header);
        recyclerView.setAdapter(adapter);
    }

    public void setHomeworkList(List<Homework> list) {
        homeworkList = list;
        if (adapter != null)
            adapter.replaceData(list);
    }
}
