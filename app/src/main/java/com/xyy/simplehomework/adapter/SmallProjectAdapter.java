package com.xyy.simplehomework.adapter;

import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.SmallProjectTitle;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class SmallProjectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_HEADER = 3;
    public static final int TYPE_PROJECT_RECORD = 2;
    public static final int TYPE_PROJECT_TOBE = 1;
    public static final int TYPE_PROJECT_FIN = 0;

    public SmallProjectAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_HEADER, R.layout.item_project_small_title);
        addItemType(TYPE_PROJECT_RECORD, R.layout.item_project_small_record);
        addItemType(TYPE_PROJECT_FIN, R.layout.item_project_small_tobe);
        addItemType(TYPE_PROJECT_TOBE, R.layout.item_project_small_tobe);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case TYPE_HEADER:
                final SmallProjectTitle title = (SmallProjectTitle) item;
                helper.setText(R.id.smallProjectTitle, title.getTitle())
                        .setImageResource(R.id.arrowForList, title.isExpanded() ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (title.isExpanded()) collapse(pos);
                        else expand(pos);
                    }
                });
                break;
            case TYPE_PROJECT_FIN:
                break;
            case TYPE_PROJECT_TOBE:
                MyProject project = (MyProject) item;
                MySubject subject = project.subject.getTarget();
                helper.setText(R.id.smallSubjectTitle, subject.name);
                ((CardView) helper.itemView)
                        .setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
                break;
            case TYPE_PROJECT_RECORD:
                subject = ((MyProject) item).subject.getTarget();
                helper.setText(R.id.smallSubjectTitle, subject.name);
                break;
        }
    }
}
