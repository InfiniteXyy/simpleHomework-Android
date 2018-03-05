package com.xyy.simplehomework.view.fragments.semester;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.helper.DateHelper;

/**
 * Created by xyy on 2018/1/29.
 */

public class SemesterFragment extends Fragment {
    private CardView weekDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_semester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup container = view.findViewById(R.id.layout);
        // set transition
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(300);
        container.setLayoutTransition(transition);
        weekDetail = view.findViewById(R.id.week_card);

        ViewModel viewModel = new ViewModel();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new MaterialMenuDrawable(
                getContext(),
                Color.BLACK,
                MaterialMenuDrawable.Stroke.THIN
        ));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null)
                    ((MainActivity) getContext()).showDrawer();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        BaseQuickAdapter<Week, BaseViewHolder> adapter
                = new BaseQuickAdapter<Week, BaseViewHolder>
                (R.layout.item_week, viewModel.getWeekList()) {
            @Override
            protected void convert(BaseViewHolder helper, Week item) {
                helper.setText(R.id.text, DateHelper.getWeekTitle(item.weekIndex));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (weekDetail.getVisibility() == View.GONE)
                    weekDetail.setVisibility(View.VISIBLE);
                else weekDetail.setVisibility(View.GONE);
            }
        });
    }
}
