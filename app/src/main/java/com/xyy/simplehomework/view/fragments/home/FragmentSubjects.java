package com.xyy.simplehomework.view.fragments.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.SubjectActivity;
import com.xyy.simplehomework.view.helper.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentSubjects extends Fragment {
    public final static int TYPE_SUBJECT = 0;
    public final static int TYPE_ADD = 1;
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
        List<MultiItemEntity> data = new ArrayList<>();
        data.addAll(subjectList);
        data.add(new AddBtn());
        SubjectListAdapter adapter = new SubjectListAdapter(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), SubjectActivity.class);
                intent.putExtra(SubjectActivity.SUBJECT_ID, ((MySubject) adapter.getItem(position)).id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
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

    class SubjectListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        SubjectListAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_SUBJECT, R.layout.item_home_subject);
            addItemType(TYPE_ADD, R.layout.item_home_subject_add);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {
            switch (helper.getItemViewType()) {
                case TYPE_SUBJECT:
                    final MySubject subject = (MySubject) item;
                    helper.setText(R.id.title, subject.getName());
                    int unfinishedNum = 0;
                    for (Homework homework : subject.homework) {
                        if (!homework.getFinished()) unfinishedNum++;
                    }
                    helper.setText(R.id.textView, unfinishedNum + "个项目未完成");
                    break;
                case TYPE_ADD:
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("添加课程")
                                    .setView(View.inflate(getContext(), R.layout.dialog_subject_add, null))
                                    .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    });
                    break;

            }

        }
    }

    class AddBtn implements MultiItemEntity {

        @Override
        public int getItemType() {
            return TYPE_ADD;
        }
    }
}
