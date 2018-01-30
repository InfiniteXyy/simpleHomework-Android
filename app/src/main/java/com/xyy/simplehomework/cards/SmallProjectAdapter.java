package com.xyy.simplehomework.cards;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyy.simplehomework.R;
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

    private Context mContext;

    private List<MySubject> hasFinished;
    private List<MySubject> toBeFinished;
    private List<MySubject> toBeRecorded;

    public SmallProjectAdapter() {
        hasFinished = new ArrayList<>();
        toBeFinished = new ArrayList<>();
        toBeRecorded = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER_FINISH;
        else if (position < hasFinished.size() + 1) return CARD_FINISH;
        else if (position == hasFinished.size() + 1) return HEADER_TOBE;
        else if (position < hasFinished.size() + toBeFinished.size() + 2) return CARD_TOBE;
        else if (position == hasFinished.size() + toBeFinished.size() + 2) return HEADER_RECORD;
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
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("待完成(0)");
                vh = new RecyclerView.ViewHolder(view){};
                break;
            case HEADER_FINISH:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("已完成(0)");
                vh = new RecyclerView.ViewHolder(view){};
                break;
            case HEADER_RECORD:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("待记录(3)");
                vh = new RecyclerView.ViewHolder(view){};
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_item, parent, false);
                vh = new SmallViewHolderCard(view);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case CARD_FINISH:
                SmallViewHolderCard vh = (SmallViewHolderCard) holder;
                vh.subjectName.setText(hasFinished.get(position - 1).name);
                break;
            case CARD_TOBE:
                vh = (SmallViewHolderCard) holder;
                MySubject subject = toBeFinished.get(position - hasFinished.size() - 2);
                vh.subjectName.setText(subject.name);
                vh.cardView.setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
                break;
            case CARD_RECORD:
                vh = (SmallViewHolderCard) holder;
                vh.subjectName.setText(toBeFinished.get(position - hasFinished.size() - toBeFinished.size() - 3).name);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return hasFinished.size() + toBeRecorded.size() + toBeFinished.size() + 3;

    }

    public void setHasFinished(List<MySubject> hasFinished) {
        this.hasFinished = hasFinished;
        notifyDataSetChanged();
    }

    public void setToBeFinished(List<MySubject> toBeFinished) {
        this.toBeFinished = toBeFinished;
        notifyDataSetChanged();
    }

    public void setToBeRecorded(List<MySubject> toBeRecorded) {
        this.toBeRecorded = toBeRecorded;
        notifyDataSetChanged();
    }

    private static class SmallViewHolderCard extends RecyclerView.ViewHolder {
        TextView subjectName;
        CardView cardView;
        public SmallViewHolderCard(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            subjectName =itemView.findViewById(R.id.smallSubjectTitle);
        }
    }
}
