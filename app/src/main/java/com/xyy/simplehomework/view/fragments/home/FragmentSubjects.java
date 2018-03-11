package com.xyy.simplehomework.view.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.Arrays;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentSubjects extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setAdapter(new BaseQuickAdapter<MySubject, BaseViewHolder>(R.layout.fragment_home_subject_item,
                Arrays.asList(new MySubject(), new MySubject(), new MySubject(), new MySubject())) {
            @Override
            protected void convert(BaseViewHolder helper, MySubject item) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        // recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}
