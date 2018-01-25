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
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_CELL = 1;

    private Context mContext;
    private List<MyProject> myProjects;

    private Box<MyProject> projectBox;
    private Box<MySubject> subjectBox;

    public ProjectAdapter() {
        this.myProjects = new ArrayList<>();
    }
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0: return TYPE_HEADER;
            default: return TYPE_CELL;
        }
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
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.hvp_header_placeholder,
                        parent, false);
                holder = new ViewHolder(view, viewType);
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.project_item,
                        parent, false);
                holder = new ViewHolder(view, viewType);

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    // 用 intent 传入下一个 activity
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        MyProject myProject = myProjects.get(position-1);
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
        }
        return holder;
    }

    public void setMyProjects(List<MyProject> projects) {
        myProjects = projects;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.type == TYPE_HEADER) return;
        MyProject myProject = myProjects.get(position-1);
        MySubject mySubject = myProject.subject.getTarget();
        holder.subjectName.setText(myProject.book);
        Glide.with(mContext).load(mySubject.imgId).into(holder.subjectImg);
    }

    @Override
    public int getItemCount() {
        return myProjects.size() + 1;
        // 头部也包含在内了
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView subjectImg;
        TextView subjectName;
        Button deleteBtn;
        int type;

        public ViewHolder(View view, int type) {
            super(view);
            this.type = type;
            if (type == TYPE_HEADER) return;
            cardView = (CardView) view;
            subjectImg = view.findViewById(R.id.subject_image);
            subjectName = view.findViewById(R.id.subject_name);
            deleteBtn = view.findViewById(R.id.card_btn_delete);
        }
    }
}
