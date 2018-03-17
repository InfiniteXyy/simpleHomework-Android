package com.xyy.simplehomework.view.fragments.week;

import android.animation.LayoutTransition;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.helper.SimpleDividerItemDecoration;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by xyy on 2018/2/22.
 */

public class DetailFragment extends Fragment implements SetDetailHandler, DatePickerDialog.OnDateSetListener {
    private View spinnerView;
    private RecyclerView recyclerView;
    private WeekHomeworkAdapter adapter;
    private RecyclerView.OnScrollListener listener;
    private List<Homework> homeworkList;
    private Comparator<Homework> deadlineComparator = new Comparator<Homework>() {
        @Override
        public int compare(Homework o1, Homework o2) {
            return o1.deadline.compareTo(o2.deadline);
        }
    };
    private Comparator<Homework> initDateComparator = new Comparator<Homework>() {
        @Override
        public int compare(Homework o1, Homework o2) {
            return o1.initDate.compareTo(o2.initDate);
        }
    };

    private Comparator<Homework> subjectComparator = new Comparator<Homework>() {
        @Override
        public int compare(Homework o1, Homework o2) {
            long i = o1.subject.getTargetId() - o2.subject.getTargetId();
            if (i == 0) return 0;
            else if (i < 0) return -1;
            else return 1;
        }
    };


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
        recyclerView = view.findViewById(R.id.week_recycler_view);
        adapter = new WeekHomeworkAdapter(R.layout.item_homework, homeworkList);

        // TODO: 图标为图示信息
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), 1));
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Collections.sort(adapter.getData(), deadlineComparator);
                        break;
                    case 1:
                        Collections.sort(adapter.getData(), initDateComparator);
                        break;
                    default:
                        Collections.sort(adapter.getData(), subjectComparator);
                        break;
                }
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
    private View lastView;

    public void onClickHomework(View view) {
        TransitionManager.endTransitions(recyclerView);
        View detail = ((View) view.getParent()).findViewById(R.id.detail);
        if (lastView != null && lastView != detail) lastView.setVisibility(View.GONE);
        boolean shouldExpand = detail.getVisibility() == View.GONE;
        detail.setVisibility(shouldExpand ? View.VISIBLE : View.GONE);
        lastView = detail;
        TransitionManager.beginDelayedTransition(recyclerView);
    }
    private Homework thisHomework;

    @Override
    public void setPlan(View view, Homework homework) {
        thisHomework = homework;
        Calendar deadline = Calendar.getInstance();
        if (homework.planDate != null)
            deadline.setTime(homework.planDate);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                DetailFragment.this,
                deadline.get(Calendar.YEAR),
                deadline.get(Calendar.MONTH),
                deadline.get(Calendar.DAY_OF_MONTH)
        );
        deadline.setTime(new Date());
        dpd.setMinDate(deadline);
        dpd.vibrate(false);// 禁止震动
        dpd.show(getActivity().getFragmentManager(), null);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        thisHomework.setPlanDate(new Date(year - 1900, monthOfYear, dayOfMonth));
    }

    class WeekHomeworkAdapter extends BaseQuickAdapter<Homework, BaseDataBindingHolder> {
        public WeekHomeworkAdapter(int layoutResId, @Nullable List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, Homework item) {
            ViewDataBinding binding = helper.getBinding();
            binding.setVariable(BR.homework, item);
            binding.setVariable(BR.handler, DetailFragment.this);
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
