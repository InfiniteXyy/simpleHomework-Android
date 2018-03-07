package com.xyy.simplehomework.view.fragments.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.freelib.multiitem.adapter.holder.DataBindViewHolderManager;
import com.freelib.multiitem.helper.ItemDragHelper;
import com.freelib.multiitem.item.UniqueItemManager;
import com.freelib.multiitem.listener.OnItemLongClickListener;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public final static String TAG = "HomeFragment";
    private List<UniqueItemManager> days;
    private View headerView;
    private ItemDragHelper dragHelper;
    private final String[] week = {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    };

    public ItemDragHelper getDragHelper() {
        return dragHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        headerView = inflater.inflate(R.layout.item_home_header, container, false);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // set tool bar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new MaterialMenuDrawable(getContext(), Color.BLACK, MaterialMenuDrawable.Stroke.THIN));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null)
                    ((MainActivity) getContext()).showDrawer();
            }
        });
        // bind with view model
        days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days.add(new UniqueItemManager(new DayViewManager(DateHelper.afterDays(i))));
        }
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getHomeworkLiveData().observe(this, new Observer<List<Homework>>() {
            @Override
            public void onChanged(List<Homework> homework) {
                for (int i = 0; i < 7; i++) {
                    List<Homework> temp = new ArrayList<>();
                    for (Homework homework1 : homework) {
                        if (homework1.planDate.equals(((DayViewManager) days.get(i).getViewHolderManager()).getDate())) {
                            temp.add(homework1);
                        }
                    }
                    ((DayViewManager) days.get(i).getViewHolderManager()).setHomeworkList(temp);
                }
            }
        });

        // set main recycler view
        RecyclerView mainRecycler = view.findViewById(R.id.recycler_view);
        final BaseItemAdapter adapter = new BaseItemAdapter();
        adapter.addHeadView(headerView);
//        PagerSnapHelper helper = new PagerSnapHelper();
//        helper.attachToRecyclerView(mainRecycler);
        adapter.addDataItems(days);
        mainRecycler.setAdapter(adapter);

        dragHelper = new ItemDragHelper(mainRecycler);
        dragHelper.setOnItemDragListener(new com.freelib.multiitem.listener.OnItemDragListener() {
            @Override
            public void onDragFinish(RecyclerView recyclerView, int recyclerPos, int itemPos) {
                super.onDragFinish(recyclerView, recyclerPos, itemPos);
            }
        });
    }


    class DayViewManager extends BaseViewHolderManager<UniqueItemManager> {
        private Calendar cal;
        private Date date;
        private List<Homework> homeworkList;
        private BaseItemAdapter adapter;

        public DayViewManager(Date date) {
            this.date = date;
            cal = Calendar.getInstance();
            cal.setTime(date);
            this.homeworkList = new ArrayList<>();
        }

        public Date getDate() {
            return date;
        }

        public List<Homework> getHomeworkList() {
            return homeworkList;
        }

        public void setHomeworkList(List<Homework> homeworkList) {
            this.homeworkList = homeworkList;
            if (adapter != null){
                adapter.setDataItems(homeworkList);
            }
        }

        private String getDayOfMonth() {
            return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        }

        private String getDayOfWeek() {
            return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
        }

        @Override
        public void onBindViewHolder(com.freelib.multiitem.adapter.holder.BaseViewHolder baseViewHolder, UniqueItemManager uniqueItemManager) {
            TextView dayNum = baseViewHolder.itemView.findViewById(R.id.day);
            TextView day = baseViewHolder.itemView.findViewById(R.id.weekIndex);
            dayNum.setText(getDayOfMonth());
            day.setText(getDayOfWeek());
        }

        @Override
        protected void onCreateViewHolder(@NonNull com.freelib.multiitem.adapter.holder.BaseViewHolder holder) {
            super.onCreateViewHolder(holder);

            View view = holder.itemView;
            RecyclerView recyclerView = getView(view, R.id.recycler_view);
            // set drag
            adapter = new BaseItemAdapter();
            adapter.register(Homework.class, new DataBindViewHolderManager<Homework>(R.layout.item_homework_tiny, BR.homework));
            adapter.setDataItems(homeworkList);
            adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                protected void onItemLongClick(com.freelib.multiitem.adapter.holder.BaseViewHolder baseViewHolder) {
                    dragHelper.startDrag(baseViewHolder);
                }
            });
            recyclerView.setAdapter(adapter);
        }


        @Override
        protected int getItemLayoutId() {
            return R.layout.item_homework_tiny_recycler;
        }
    }
}
