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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Suggestion;
import com.xyy.simplehomework.view.MainActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/3/11.
 */

public class FragmentMine extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Suggestion> suggestions = Arrays.asList(
                new Suggestion("吃早饭", R.drawable.breakfast),
                new Suggestion("早睡觉", R.drawable.sleep),
                new Suggestion("学习新知识", R.drawable.learn_something_new)
        );
        view.findViewById(R.id.button).setOnClickListener(v -> ((MainActivity) getActivity()).showAddDialog(null));
        RecyclerView suggestRecycler = view.findViewById(R.id.recycler_view);
        SuggestionAdapter adapter = new SuggestionAdapter(R.layout.item_suggestion, suggestions);
        suggestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestRecycler.setAdapter(adapter);
    }

    public static class SuggestionAdapter extends BaseQuickAdapter<Suggestion, BaseViewHolder> {
        SuggestionAdapter(int layoutResId, @Nullable List<Suggestion> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Suggestion item) {
            helper.setText(R.id.title, item.getTitle());
            Glide.with(mContext).load(item.getImgRes()).into((ImageView) helper.getView(R.id.img));
        }
    }
}
