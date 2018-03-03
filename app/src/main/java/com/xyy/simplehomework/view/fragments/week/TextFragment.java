package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
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

import java.util.List;

/**
 * Created by xyy on 2018/3/3.
 */

public class TextFragment extends Fragment {
    private WeekUIInteraction mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof WeekUIInteraction) {
            mListener = (WeekUIInteraction) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement WeekUIInteraction");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewModel viewModel = mListener.getViewModel();
        List<MySubject> subjects = viewModel.getSubjectList();
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new BaseQuickAdapter<MySubject, BaseViewHolder>(R.layout.item_text_subject, subjects) {
            @Override
            protected void convert(BaseViewHolder helper, MySubject item) {
                helper.setText(R.id.subject_name, item.getName());
                RecyclerView homeworkRecycler = helper.getView(R.id.recycler_view);
                homeworkRecycler.setAdapter(new BaseQuickAdapter<Homework, BaseViewHolder>(R.layout.item_text_homework, item.homework) {
                    @Override
                    protected void convert(BaseViewHolder helper, Homework item) {
                        helper.setText(R.id.homework, item.getTitle());
                    }
                });
            }
        });

    }
}
