package com.xyy.simplehomework.cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xyy.simplehomework.App;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.ProjectActivity;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{
    private Context mContext;
    private List<MyProject> myProjects;

    private Box<MyProject> projectBox;
    private Box<MySubject> subjectBox;

    public ProjectAdapter() {
        this.myProjects = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        BoxStore box = ((App) ((Activity) mContext).getApplication()).getBoxStore();
        projectBox = box.boxFor(MyProject.class);
        subjectBox = box.boxFor(MySubject.class);

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
                MyProject myProject = myProjects.get(position);
                MySubject thisSubject = subjectBox.get(myProject.subject.getTargetId());

                Intent intent = new Intent(mContext, ProjectActivity.class);
                intent.putExtra(ProjectActivity.PROJECT_ID, projectBox.getId(myProject));
                intent.putExtra(ProjectActivity.SUBJECT_ID, subjectBox.getId(thisSubject));
                mContext.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "删除了！", Toast.LENGTH_SHORT).show();
                projectBox.remove(myProjects.get(holder.getAdapterPosition()));

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
        holder.subjectName.setText(myProject.book);
        Glide.with(mContext).load(mySubject.imgId).into(holder.subjectImg);
    }

    @Override
    public int getItemCount() {
        return myProjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView subjectImg;
        TextView subjectName;
        Button deleteBtn;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            subjectImg = view.findViewById(R.id.subject_image);
            subjectName = view.findViewById(R.id.subject_name);
            deleteBtn = view.findViewById(R.id.card_btn_delete);
        }
    }
}
