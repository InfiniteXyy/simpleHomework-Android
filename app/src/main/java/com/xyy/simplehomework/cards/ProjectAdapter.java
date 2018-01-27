package com.xyy.simplehomework.cards;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xyy.simplehomework.MainActivity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;

import java.util.ArrayList;
import java.util.List;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context mContext;
    private List<MyProject> myProjects;

    public ProjectAdapter() {
        this.myProjects = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view;
        final ViewHolder holder;

        view = LayoutInflater.from(mContext).inflate(R.layout.project_item,
                parent, false);
        holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            // 用 intent 传入下一个 activity
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ((MainActivity) mContext).showProjectsDetail(myProjects.get(position));
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "删除了！", Toast.LENGTH_SHORT).show();
                ((MainActivity) mContext).finishProject(myProjects.get(holder.getAdapterPosition()));
            }
        });
        return holder;
    }

    void setMyProjects(List<MyProject> projects) {
        myProjects = projects;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyProject myProject = myProjects.get(position);
        MySubject mySubject = myProject.subject.getTarget();
        holder.subjectName.setText(mySubject.name);
        holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(mySubject.colorId));
//        Glide.with(mContext).load(mySubject.imgId).into(holder.subjectImg);
    }

    @Override
    public int getItemCount() {
        return myProjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        //        ImageView subjectImg;
        TextView subjectName;
        Button deleteBtn;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
//            subjectImg = view.findViewById(R.id.subject_image);
            subjectName = view.findViewById(R.id.subject_name);
            deleteBtn = view.findViewById(R.id.card_btn_delete);
        }
    }
}
