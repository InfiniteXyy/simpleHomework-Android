package com.xyy.simplehomework.cards;

import android.content.Context;
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
    private static final int HEADER_TOBE = 2;
    private static final int HEADER_RECORD = 3;
    private static final int CARD = 4;
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
        else if (position == hasFinished.size() + 1) return HEADER_TOBE;
        else if (position == hasFinished.size() + toBeFinished.size() + 2) return HEADER_RECORD;
        else return CARD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view;
        switch (viewType) {
            case HEADER_TOBE:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("待完成(0)");
                break;
            case HEADER_FINISH:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("已完成(0)");
                break;
            case HEADER_RECORD:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_title, parent, false);
                ((TextView) view.findViewById(R.id.smallProjectTitle)).setText("待记录(3)");
                break;
            case CARD:
                view = LayoutInflater.from(mContext).inflate(R.layout.small_project_item, parent, false);
                break;
            default: return null;
        }
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return hasFinished.size() + toBeRecorded.size() + toBeRecorded.size() + 3;
    }
}
