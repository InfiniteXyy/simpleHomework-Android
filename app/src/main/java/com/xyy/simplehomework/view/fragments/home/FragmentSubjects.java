package com.xyy.simplehomework.view.fragments.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.SubjectActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject Fragment for viewing all subject.
 * every single card for overview and act as a button into {@link SubjectActivity}
 */

public class FragmentSubjects extends Fragment implements View.OnClickListener {
    public final static int TYPE_SUBJECT = 0;
    public final static int TYPE_ADD = 1;
    private RecyclerView recyclerView;
    private List<MySubject> subjectList = new ArrayList<>();
    private HomeUIInteraction mListener;
    private SubjectListAdapter adapter;

    public static FragmentSubjects newInstance(List<MySubject> subjects, HomeUIInteraction mListener) {
        FragmentSubjects fragmentSubjects = new FragmentSubjects();
        fragmentSubjects.setSubjectList(subjects);
        fragmentSubjects.mListener = mListener;
        return fragmentSubjects;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new SubjectListAdapter(subjectList);
        adapter.setOnItemClickListener((adapter, view1, position) -> {
            Intent intent = new Intent(getContext(), SubjectActivity.class);
            intent.putExtra(SubjectActivity.SUBJECT_ID, ((MySubject) adapter.getItem(position)).id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
    }

    public void setSubjectList(List<MySubject> subjectList) {
        this.subjectList.clear();
        this.subjectList.addAll(subjectList);
        this.subjectList.add(new AddBtn());
        if (adapter != null) {
            adapter.replaceData(this.subjectList);
        }

    }

    @Override
    public void onClick(View view) {
        final View addDialog = View.inflate(getContext(), R.layout.dialog_subject_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("添加课程")
                .setView(view)
                .setPositiveButton("添加", (dialog, which) -> {
                    String name = ((TextInputEditText) addDialog.findViewById(R.id.name)).getText().toString();
                    if (!name.trim().equals(""))
                        mListener.putSubject(new MySubject(name));
                    else
                        Toast.makeText(getContext(), "请输入正确的学科名称", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }

    class SubjectListAdapter extends BaseMultiItemQuickAdapter<MySubject, BaseViewHolder> {
        SubjectListAdapter(List<MySubject> data) {
            super(data);
            addItemType(TYPE_SUBJECT, R.layout.item_home_subject);
            addItemType(TYPE_ADD, R.layout.item_home_subject_add);
        }

        @Override
        protected void convert(BaseViewHolder helper, MySubject item) {
            switch (helper.getItemViewType()) {
                case TYPE_SUBJECT:
                    helper.setText(R.id.title, item.getName());
                    int unfinishedNum = 0;
                    for (Homework homework : item.homework) {
                        if (!homework.getFinished()) unfinishedNum++;
                    }
                    helper.setText(R.id.textView, unfinishedNum + "个项目未完成");
                    break;
                case TYPE_ADD:
                    helper.itemView.setOnClickListener(FragmentSubjects.this);
                    break;

            }
        }
    }

    class AddBtn extends MySubject {
        @Override
        public int getItemType() {
            return TYPE_ADD;
        }
    }
}
