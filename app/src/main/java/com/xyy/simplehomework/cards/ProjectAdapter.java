package com.xyy.simplehomework.cards;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;

import java.util.List;


public class ProjectAdapter extends BaseQuickAdapter<MyProject, BaseViewHolder> {
    ProjectAdapter(int layoutResId, List<MyProject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyProject item) {
        MySubject subject = item.subject.getTarget();
        helper.setText(R.id.subject_name, subject.name)
                .addOnClickListener(R.id.card_btn_delete);
        ((CardView) helper.getView(R.id.project_card_view))
                .setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
    }

}
