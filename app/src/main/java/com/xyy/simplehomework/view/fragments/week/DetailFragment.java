package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.SimpleDividerItemDecoration;
import com.xyy.simplehomework.view.holder.BaseDataBindingHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xyy on 2018/2/22.
 */

public class DetailFragment extends Fragment {
    private WeekUIInteraction mListener;
    private View spinnerView;
    private WeekHomeworkAdapter adapter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        spinnerView = inflater.inflate(R.layout.item_small_title, container, false);
        return inflater.inflate(R.layout.fragment_week_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeworkList = new ArrayList<>();
        // first, set main recyclerView
        RecyclerView weekRecyclerView = view.findViewById(R.id.week_recycler_view);
        adapter = new WeekHomeworkAdapter(R.layout.item_homework_detail, homeworkList);
        Collections.sort(adapter.getData(), deadlineComparator); // default sort by deadline
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mListener.onClickHomework((Homework) adapter.getItem(position));
            }
        });
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(weekRecyclerView);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        weekRecyclerView.setAdapter(adapter);
        weekRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), 1));

        // second, set small subject view on the top
        WeekHeaderAdapter subjectHeaderAdapter = new WeekHeaderAdapter(R.layout.item_project,
                mListener.getSubjectList());

        final RecyclerView headerRecycler = view.findViewById(R.id.subject_recycler_view);
        headerRecycler.setAdapter(subjectHeaderAdapter);
        subjectHeaderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                filterBySubject((MySubject) adapter.getItem(position));
            }
        });
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.replaceData(homeworkList);
            }
        });

        // finally, add spinner to the main recycler
        AppCompatSpinner spinner = spinnerView.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item_text,
                getResources().getStringArray(R.array.week_homework_show_type));
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinnerView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mListener.showAddDialog();
            }
        });
        adapter.addHeaderView(spinnerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

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

    public void filterBySubject(MySubject subject) {
        List<Homework> singleHomeworkList = new ArrayList<>();
        for (Homework homework : homeworkList) {
            if (homework.subject.getTargetId() == subject.id) {
                singleHomeworkList.add(homework);
            }
        }
        adapter.replaceData(singleHomeworkList);
    }


    public static class WeekHomeworkAdapter extends BaseItemDraggableAdapter<Homework, BaseDataBindingHolder> {
        public WeekHomeworkAdapter(int layoutResId, @Nullable List<Homework> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, Homework item) {
            ViewDataBinding binding = helper.getBinding();
            binding.setVariable(BR.homework, item);
            // prepare UI before show
            binding.executePendingBindings();
            GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
            circle.setColor(item.subject.getTarget().color);
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

    public static class WeekHeaderAdapter extends BaseQuickAdapter<MySubject, BaseViewHolder> {
        public WeekHeaderAdapter(int layoutResId, @Nullable List<MySubject> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MySubject item) {
            GradientDrawable circle = (GradientDrawable) helper.getView(R.id.circle).getBackground();
            circle.setColor(item.color);
            helper.setText(R.id.circle, item.getName().substring(0, 1));
        }
    }

}
