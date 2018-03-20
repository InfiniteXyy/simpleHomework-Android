package com.xyy.simplehomework.view.fragments.week;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.handler.HomeworkHandler;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;
import com.xyy.simplehomework.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xyy on 2018/2/22.
 */

public class DetailFragment extends Fragment {
    private static final String TAG = "DetailFragment";
    private View spinnerView;
    private WeekHomeworkAdapter adapter;
    private RecyclerView.OnScrollListener listener;
    private List<Homework> homeworkList;
    private Comparator<Homework> deadlineComparator = (o1, o2) -> o1.deadline.compareTo(o2.deadline);
    private Comparator<Homework> initDateComparator = (o1, o2) -> o1.initDate.compareTo(o2.initDate);
    private Comparator<Homework> subjectComparator = (o1, o2) -> (int) (o1.subject.getTargetId() - o2.subject.getTargetId());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        spinnerView = inflater.inflate(R.layout.item_small_title, container, false);
        return inflater.inflate(R.layout.fragment_week_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeworkList = new ArrayList<>();
        // first, set main recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.week_recycler_view);
        adapter = new WeekHomeworkAdapter(R.layout.item_homework, homeworkList);

        recyclerView.addOnScrollListener(listener);
        // finally, add spinner to the main recycler
        AppCompatSpinner spinner = spinnerView.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item_text,
                getResources().getStringArray(R.array.week_homework_show_type));
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        adapter.addHeaderView(spinnerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(R.layout.empty_view);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Homework> data = adapter.getData();
                switch (position) {
                    case 0:
                        Collections.sort(data, deadlineComparator);
                        break;
                    case 1:
                        Collections.sort(data, initDateComparator);
                        break;
                    default:
                        Collections.sort(data, subjectComparator);
                        break;
                }
                Collections.sort(data, ((o1, o2) -> o2.getFinished() ? 1 : 0));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setHomeworkList(List<Homework> list) {
        homeworkList = list;
        adapter.replaceData(homeworkList);
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        listener = onScrollListener;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        MainViewModel.getInstance().popHomework();
    }

    class WeekHomeworkAdapter extends BaseQuickAdapter<Homework, BaseDataBindingHolder> {
        private HomeworkHandler handler;

        WeekHomeworkAdapter(int layoutResId, @Nullable List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, Homework item) {
            if (handler == null) {
                handler = new HomeworkHandler(getContext(), MainViewModel.getInstance());
            }
            ViewDataBinding binding = helper.getBinding();
            binding.setVariable(BR.homework, item);
            binding.setVariable(BR.handler, handler);
            binding.executePendingBindings();
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
