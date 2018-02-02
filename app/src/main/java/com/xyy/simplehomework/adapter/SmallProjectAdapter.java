package com.xyy.simplehomework.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.entity.SectionProject;

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
        switch (item.t.status) {
            case MyProject.TOBE_DONE:
                ((CardView) helper.getView(R.id.small_project_card_view))
                        .setCardBackgroundColor(mContext.getResources().getColor(subject.colorId));
                break;
            case MyProject.TOBE_RECORD:
                helper.getView(R.id.small_project_card_view).setAlpha(0.6f);
                ((TextView) helper.getView(R.id.smallSubjectTitle))
                        .setTextColor(Color.BLACK);
                break;
            default:
                break;
        }

    }
}
