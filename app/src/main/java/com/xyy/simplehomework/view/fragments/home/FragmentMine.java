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
import com.xyy.simplehomework.entity.Article;
import com.xyy.simplehomework.entity.Suggestion;
import com.xyy.simplehomework.view.MainActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Main Fragment for home page.
 * Including <em>suggestion/article/profile</em>
 */

public class FragmentMine extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Suggestion> suggestions = Arrays.asList(
                new Suggestion("课堂作业", "完成一篇实验报告 (计算机网络)"),
                new Suggestion("自学", "搭建Ruby on Rails环境"),
                new Suggestion("锻炼", "下午跑步 2km")
        );
        List<Article> articles = Arrays.asList(
                new Article(),
                new Article(),
                new Article()
        );
        view.findViewById(R.id.button).setOnClickListener(v -> ((MainActivity) getActivity()).showAddDialog(null));
        RecyclerView suggestRecycler = view.findViewById(R.id.recycler_view);
        SuggestionAdapter adapter = new SuggestionAdapter(R.layout.item_suggestion, suggestions);
        suggestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestRecycler.setAdapter(adapter);

        RecyclerView articleRecycler = view.findViewById(R.id.recycler_view2);
        ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.item_home_headline, articles);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        articleRecycler.setLayoutManager(manager);
        articleRecycler.setAdapter(articleAdapter);

    }

    public static class SuggestionAdapter extends BaseQuickAdapter<Suggestion, BaseViewHolder> {
        SuggestionAdapter(int layoutResId, @Nullable List<Suggestion> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Suggestion item) {
            helper.setText(R.id.title, item.getTitle());
            helper.setText(R.id.detail, item.getDetail());
        }
    }

    public static class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {
        ArticleAdapter(int layoutResId, @Nullable List<Article> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Article item) {
            helper.setText(R.id.title, item.getTitle());
            helper.setText(R.id.time, item.getTime());
            Glide.with(mContext).load(item.getImgRes()).into((ImageView) helper.getView(R.id.image));
        }
    }
}
