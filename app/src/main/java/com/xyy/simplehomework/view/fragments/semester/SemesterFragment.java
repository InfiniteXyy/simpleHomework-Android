package com.xyy.simplehomework.view.fragments.semester;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/29.
 */

public class SemesterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_semester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        List<String> arrays = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrays.add(Integer.toString(i));
        }
        SimpleTest test = new SimpleTest(R.layout.item_small_title, arrays);
        recyclerView.setAdapter(test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public class SimpleTest extends BaseQuickAdapter<String, BaseViewHolder> {

        public SimpleTest(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.button, item);
        }
    }
}
