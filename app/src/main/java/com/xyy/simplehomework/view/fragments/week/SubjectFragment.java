package com.xyy.simplehomework.view.fragments.week;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.List;

public class SubjectFragment extends Fragment {
    private View headerView;
    private WeekUIInteraction mListener;

    public SubjectFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof WeekUIInteraction) {
            mListener = (WeekUIInteraction) getParentFragment();
        } else {
            throw new RuntimeException("parent Fragment should implement WeekUIInteraction");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_subject, container, false);
        headerView = inflater.inflate(R.layout.item_small_title, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // first set subject recycler
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SubjectAdapter adapter = new SubjectAdapter(R.layout.item_subject, mListener.getSubjectList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // second, set spinner
        AppCompatSpinner spinner = headerView.findViewById(R.id.spinner);
        TextView btn = headerView.findViewById(R.id.button);
        btn.setText("管理");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                R.layout.spinner_item_text,
                getResources().getStringArray(R.array.week_subject_show_type));
        arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        adapter.addHeaderView(headerView);
    }

    private static class SubjectAdapter extends BaseQuickAdapter<MySubject, BaseViewHolder> {

        public SubjectAdapter(int layoutResId, @Nullable List<MySubject> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MySubject item) {
            helper.setText(R.id.subject_name, item.getName());
            ((GradientDrawable) helper.getView(R.id.circle).getBackground()).setColor(item.color);
        }
    }

}
