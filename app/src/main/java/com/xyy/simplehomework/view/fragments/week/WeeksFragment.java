package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
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
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/12.
 */

public class WeeksFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Week> weeks;
    BaseQuickAdapter<Week, BaseViewHolder> adapter;
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        mListener.needBtns(hidden);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        weeks = new ArrayList<>();
        for (int i = 0; i <= DateHelper.getWeekIndex(); i++) {
            weeks.add(new Week(i));
        }

        adapter = new BaseQuickAdapter<Week, BaseViewHolder>(R.layout.item_week_weeks, weeks) {
            @Override
            protected void convert(BaseViewHolder helper, Week item) {
                helper.setText(R.id.textView, item.getWeekName());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mListener.onClickWeek(position);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

}
