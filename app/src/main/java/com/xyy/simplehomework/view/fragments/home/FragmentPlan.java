package com.xyy.simplehomework.view.fragments.home;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.helper.DateHelper;
import com.xyy.simplehomework.view.fragments.home.dialog.DialogTask;
import com.xyy.simplehomework.view.fragments.home.domain.PlanSection;
import com.xyy.simplehomework.view.handler.HomeworkHandler;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Plan Fragment for home page.
 * used to view or modify homework plan
 */

public class FragmentPlan extends Fragment implements BaseQuickAdapter.OnItemChildClickListener {
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
    private MainViewModel viewModel;

    public static FragmentPlan newInstance(List<Homework> homeworkList) {
        FragmentPlan fragmentPlan = new FragmentPlan();
        fragmentPlan.setSections();
        fragmentPlan.classifyPlanHomework(homeworkList);
        return fragmentPlan;
    }

    @Override
    public void onAttach(Context context) {
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        Log.d("view", "onAttach: "+viewModel);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return recyclerView = new RecyclerView(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new ExpandablePlanAdapter(sections);
        adapter.setOnItemChildClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setSections() {
        sections = new ArrayList<>();
        for (String name : sectionNames) {
            sections.add(new PlanSection(name));
        }
    }

    public void classifyPlanHomework(List<Homework> homeworkList) {
        setSections();
        for (Homework homework : homeworkList) {
            if (homework.getFinished()) continue; // ignore finished homework
            if (homework.planDate == null) {
                ((PlanSection) sections.get(3)).addSubItem(homework);
            } else if (homework.planDate.equals(DateHelper.getToday())) {
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.add_plan, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.task:
                    new DialogTask().show(getChildFragmentManager(), null);
                    break;
                case R.id.homework:
                    break;
            }
            return false;
        });
    }

    class ExpandablePlanAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseDataBindingHolder> {
        private HomeworkHandler handler;

        ExpandablePlanAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(TYPE_PLAN, R.layout.item_homework);
            addItemType(TYPE_SECTION, R.layout.item_home_plan_section);
        }

        @Override
        protected void convert(final BaseDataBindingHolder helper, final MultiItemEntity item) {
            if (handler == null) {
                handler = new HomeworkHandler(mContext, viewModel);
            }
            switch (helper.getItemViewType()) {
                case TYPE_PLAN:
                    helper.getBinding().setVariable(BR.homework, item);
                    helper.getBinding().setVariable(BR.handler, handler);
                    break;
                case TYPE_SECTION:
                    final int badge = ((PlanSection) item).getSubItems() == null ? 0 : ((PlanSection) item).getSubItems().size();
                    PlanSection section = (PlanSection) item;
                    boolean showAdd = badge == 0 || section.isExpanded();
                    helper.setGone(R.id.add, showAdd);
                    helper.setGone(R.id.badge, !showAdd);
                    helper.setText(R.id.text, ((PlanSection) item).getSectionName());
                    helper.setText(R.id.badge_num, String.valueOf(badge));
                    helper.itemView.setOnClickListener(v -> {
                        int pos = helper.getAdapterPosition();
                        if (section.isExpanded()) {
                            collapse(pos);
                        } else if (badge != 0) { // expand only when there exists some sub items
                            expand(pos);
                        }
                    });
                    helper.addOnClickListener(R.id.add);
                    break;
            }
        }

        @Override
        protected View getItemView(int layoutResId, ViewGroup parent) {
            ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
            if (binding == null) {
                return super.getItemView(layoutResId, parent);
            }
            View view = binding.getRoot();
            view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
            return view;
        }
    }

}
