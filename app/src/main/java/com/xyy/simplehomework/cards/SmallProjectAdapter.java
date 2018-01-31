package com.xyy.simplehomework.cards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class SmallProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_FINISH = 0;
    private static final int HEADER_TOBE = 1;
    private static final int HEADER_RECORD = 2;
    private static final int CARD_FINISH = 3;
    private static final int CARD_TOBE = 4;
    private static final int CARD_RECORD = 5;
    private int finishItems, tobeDoneItems, notRecordItems;
    private Context mContext;

    private List<MyProject> projectList;

    public SmallProjectAdapter() {
        projectList = new ArrayList<>();
        finishItems = 0;
        tobeDoneItems = 0;
        notRecordItems = 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER_FINISH;
        else if (position < finishItems + 1) return CARD_FINISH;
        else if (position == finishItems + 1) return HEADER_TOBE;
        else if (position < finishItems + tobeDoneItems + 2) return CARD_TOBE;
        else if (position == finishItems + tobeDoneItems + 2) return HEADER_RECORD;
        else return CARD_RECORD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view;
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case HEADER_TOBE:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                vh = new RecyclerView.ViewHolder(view) {
                };
                break;
            case HEADER_FINISH:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                vh = new RecyclerView.ViewHolder(view) {
                };
                break;
            case HEADER_RECORD:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                vh = new RecyclerView.ViewHolder(view) {
                };
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_item, parent, false);
                vh = new SmallViewHolderCard(view);
                break;
        }
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case CARD_FINISH:
                SmallViewHolderCard vh = (SmallViewHolderCard) holder;
                MyProject project = projectList.get(position - 1);
                MySubject subject = project.subject.getTarget();
                vh.subjectName.setText(subject.name);
                break;
            case CARD_TOBE:
                vh = (SmallViewHolderCard) holder;
                project = projectList.get(position - 2);
                subject = project.subject.getTarget();
                vh.subjectName.setText(subject.name);
                vh.cardView.setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
                break;
            case CARD_RECORD:
                vh = (SmallViewHolderCard) holder;
                vh.subjectName.setTextColor(Color.BLACK);
                project = projectList.get(position - 3);
                subject = project.subject.getTarget();
                vh.subjectName.setText(subject.name);
                vh.cardView.setAlpha(0.5f);
                vh.subjectName.setAlpha(0.7f);
                break;
            case HEADER_FINISH:
                ((TextView) holder.itemView.findViewById(R.id.smallProjectTitle)).setText(String.format("已完成(%d)", finishItems));
                break;
            case HEADER_RECORD:
                ((TextView) holder.itemView.findViewById(R.id.smallProjectTitle)).setText(String.format("待记录(%d)", notRecordItems));
                break;
            case HEADER_TOBE:
                ((TextView) holder.itemView.findViewById(R.id.smallProjectTitle)).setText(String.format("未完成(%d)", tobeDoneItems));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return projectList.size() + 3;
    }

    public void updateList(List<MyProject> list) {
        this.projectList = list;
        finishItems = 0;
        tobeDoneItems = 0;
        notRecordItems = 0;
        for (MyProject myProject : list) {
            switch (myProject.status) {
                case MyProject.HAS_FINISHED:finishItems++;break;
                case MyProject.TOBE_DONE:tobeDoneItems++;break;
                case MyProject.TOBE_RECORD:notRecordItems++;break;
            }
        }
        notifyDataSetChanged();
    }


    private static class SmallViewHolderCard extends RecyclerView.ViewHolder {
        TextView subjectName;
        CardView cardView;

        public SmallViewHolderCard(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            subjectName = itemView.findViewById(R.id.smallSubjectTitle);
        }
    }
}
