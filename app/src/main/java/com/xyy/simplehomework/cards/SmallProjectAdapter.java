package com.xyy.simplehomework.cards;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.List;

/**
 * Created by xyy on 2018/1/28.
 */

public class SmallProjectAdapter extends BaseSectionQuickAdapter<SectionProject, BaseViewHolder> {

    public SmallProjectAdapter(int layoutResId, int sectionHeadResId, List<SectionProject> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionProject item) {
        helper.setText(R.id.smallProjectTitle, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionProject item) {
        MySubject subject = item.t.subject.getTarget();
        helper.setText(R.id.smallSubjectTitle, subject.name);
        ((CardView) helper.getView(R.id.small_project_card_view))
                .setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
    }
}
