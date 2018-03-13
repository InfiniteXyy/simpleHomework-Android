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

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentPlan extends Fragment {
    public static final int TYPE_SECTION = 0;
    public static final int TYPE_PLAN = 1;
    private static final String[] sectionNames = {
            "今天",
            "明天",
            "即将来临",
            "搁置"
    };
    private RecyclerView recyclerView;
    private List<MultiItemEntity> sections;
    private ExpandablePlanAdapter adapter;

    public static FragmentPlan newInstance(List<Homework> homeworkList) {
        FragmentPlan fragmentPlan = new FragmentPlan();
        fragmentPlan.setSections();
        fragmentPlan.classifyPlanHomework(homeworkList);
        return fragmentPlan;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getContext());
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpandablePlanAdapter(sections);
        recyclerView.setAdapter(adapter);
    }

    private void setSections() {
        sections = new ArrayList<>();
        for (String name : sectionNames) {
            sections.add(new PlanSection(name));
        }

    }

    public void classifyPlanHomework(List<Homework> homeworkList) {
        for (int i = 0; i < 4; i++) {
            ((PlanSection) sections.get(i)).removeSubItems();
        }

        for (Homework homework : homeworkList) {
            if (homework.planDate == null) {
                ((PlanSection) sections.get(3)).addSubItem(homework);
            } else if (homework.planDate == DateHelper.getToday()) {
                ((PlanSection) sections.get(0)).addSubItem(homework);
            } else if (DateHelper.afterDayNum(homework.planDate) == 1) {
                ((PlanSection) sections.get(1)).addSubItem(homework);
            } else {
                ((PlanSection) sections.get(2)).addSubItem(homework);
            }
        }
        if (adapter != null)
            adapter.replaceData(sections);
    }

    class ExpandablePlanAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        ExpandablePlanAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_PLAN, R.layout.item_home_plan_item);
            addItemType(TYPE_SECTION, R.layout.item_home_plan_section);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
            switch (helper.getItemViewType()) {
                case TYPE_PLAN:
                    helper.setText(R.id.text, ((Homework) item).getTitle());
                    break;
                case TYPE_SECTION:
                    helper.setText(R.id.text, ((PlanSection) item).getSectionName());
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (((PlanSection) item).isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
                            }
                        }
                    });
                    break;
            }
        }
    }

}
