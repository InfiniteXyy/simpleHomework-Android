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

    private static final int VIEW_EMPTY = 1;
    private static final int VIEW_CARD = 2;

    ProjectAdapter() {
        this.myProjects = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        if (myProjects.isEmpty()) return VIEW_EMPTY;
        else return VIEW_CARD;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view;
        ViewHolder viewHolder;
        switch (viewType) {
            case VIEW_EMPTY:
                view = LayoutInflater.from(mContext).inflate(R.layout.empty_view, parent, false);
                viewHolder = new ViewHolderEmpty(view);
                break;
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.project_item,
                        parent, false);
                viewHolder = new ViewHolderCard(view);
                break;
        }
        return viewHolder;
    }

    void setMyProjects(List<MyProject> projects) {
        myProjects = projects;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) != VIEW_EMPTY) {
            final ViewHolderCard cardHolder = (ViewHolderCard) holder;
            MyProject myProject = myProjects.get(position);
            MySubject mySubject = myProject.subject.getTarget();
            cardHolder.subjectName.setText(mySubject.name);
            cardHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(mySubject.colorId));

            cardHolder.cardView.setOnClickListener(new View.OnClickListener() {
                // 用 intent 传入下一个 activity
                @Override
                public void onClick(View v) {
                    int position = cardHolder.getAdapterPosition();
                    ((MainActivity) mContext).showProjectsDetail(myProjects.get(position));
                }
            });

            cardHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "删除了！", Toast.LENGTH_SHORT).show();
                    ((MainActivity) mContext).finishProject(myProjects.get(cardHolder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (myProjects.isEmpty()) return 1;
        return myProjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
        }
    }

    static class ViewHolderCard extends ViewHolder {
        CardView cardView;
        TextView subjectName;
        Button deleteBtn;

        ViewHolderCard(View view) {
            super(view);
            cardView = (CardView) view;
            subjectName = view.findViewById(R.id.subject_name);
            deleteBtn = view.findViewById(R.id.card_btn_delete);
        }
    }

    static class ViewHolderEmpty extends ViewHolder {

        ViewHolderEmpty(View view) {
            super(view);
        }
    }
}
