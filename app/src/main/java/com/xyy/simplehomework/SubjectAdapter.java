package com.xyy.simplehomework;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    private Context mContext;

    private List<MySubject> mySubjects;

    public SubjectAdapter(List<MySubject> mySubjects) {
        this.mySubjects = mySubjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.subject_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MySubject mySubject = mySubjects.get(position);
        holder.subjectName.setText(mySubject.getName());
        Glide.with(mContext).load(mySubject.getImgId()).into(holder.subjectImg);
    }

    @Override
    public int getItemCount() {
        return mySubjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView subjectImg;
        TextView subjectName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            subjectImg = view.findViewById(R.id.subject_image);
            subjectName = view.findViewById(R.id.subject_name);
        }
    }
}
