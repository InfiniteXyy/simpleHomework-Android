package com.xyy.simplehomework.view.fragments.home;

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

import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentPlan extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<PlanSection> sections = Arrays.asList(
                new PlanSection("今天"),
                new PlanSection("明天"),
                new PlanSection("即将来临"),
                new PlanSection("搁置")
        );
        recyclerView.setAdapter(new BaseQuickAdapter<PlanSection, BaseViewHolder>(R.layout.item_home_plan, sections) {
            @Override
            protected void convert(BaseViewHolder helper, PlanSection item) {
                helper.setText(R.id.text, item.getSectionName());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
