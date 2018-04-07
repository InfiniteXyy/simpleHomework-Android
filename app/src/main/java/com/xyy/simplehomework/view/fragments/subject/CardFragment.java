package com.xyy.simplehomework.view.fragments.subject;

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
import com.xyy.simplehomework.entity.Card;

import java.util.Arrays;

/**
 * An inside card fragment for specified {@link Card}
 */
public class CardFragment extends Fragment {
    private View header;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        header = inflater.inflate(R.layout.header_subject_card, container, false);
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_subject_homework, Arrays.asList("1", "2", "3", "4")) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(header);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
