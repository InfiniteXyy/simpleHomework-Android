package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/12.
 */

public class WeeksFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Week> weeks;
    BaseQuickAdapter<Week, BaseViewHolder> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<Week, BaseViewHolder>(R.layout.item_week_weeks, weeks) {
            @Override
            protected void convert(BaseViewHolder helper, Week item) {
                helper.setText(R.id.textView, item.getWeekName());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
        if (adapter != null)
            adapter.replaceData(weeks);
    }
}
