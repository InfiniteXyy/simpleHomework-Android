package com.xyy.simplehomework.view.fragments.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Day;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private HomeAdapter adapter;
    private List<Day> days;
    private View headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        headerView = inflater.inflate(R.layout.item_home_header, container, false);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days.add(new Day(DateHelper.afterDays(i)));
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new MaterialMenuDrawable(getContext(), Color.BLACK, MaterialMenuDrawable.Stroke.THIN));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null)
                    ((MainActivity) getContext()).showDrawer();
            }
        });
        adapter = new HomeAdapter(R.layout.item_homework_tiny_recycler, days);
        recyclerView.setAdapter(adapter);

        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getHomeworkLiveData().observe(this, new Observer<List<Homework>>() {
            @Override
            public void onChanged(List<Homework> homework) {
                for (int i = 0; i < 7; i++) {
                    List<Homework> temp = new ArrayList<>();
                    for (Homework homework1 : homework) {
                        if (homework1.planDate.equals(days.get(i).getDate())) {
                            temp.add(homework1);
                        }
                    }
                    days.get(i).setHomeworkList(temp);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        // add header
        adapter.addHeaderView(headerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class HomeAdapter extends BaseQuickAdapter<Day, BaseViewHolder> {


        public HomeAdapter(int layoutResId, @Nullable List<Day> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Day item) {
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            HomeworkAdapter adapter = new HomeworkAdapter(R.layout.item_homework_tiny, item.getHomeworkList());
            recyclerView.setAdapter(adapter);
            helper.setText(R.id.day, item.getDayOfMonth());
            helper.setText(R.id.weekIndex, item.getDayOfWeek());
        }
    }

    public static class HomeworkAdapter extends BaseQuickAdapter<Homework, BaseDataBindingHolder> {

        public HomeworkAdapter(int layoutResId, @Nullable List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, Homework item) {
            ViewDataBinding binding = helper.getBinding();
            binding.setVariable(BR.homework, item);
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
