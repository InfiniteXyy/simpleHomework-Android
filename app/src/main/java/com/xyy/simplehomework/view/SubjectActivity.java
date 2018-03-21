package com.xyy.simplehomework.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.Arrays;

import io.objectbox.BoxStore;

public class SubjectActivity extends AppCompatActivity {
    public static final String SUBJECT_ID = "subject_id";
    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        BoxStore boxStore = App.getInstance().getBoxStore();
        subject = boxStore.boxFor(MySubject.class).get(subject_id);

        RecyclerView cardRecycler = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        cardRecycler.setLayoutManager(manager);
        cardRecycler.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_subject_card, Arrays.asList("1", "2", "3", "4")) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(cardRecycler);
        ((TextView) findViewById(R.id.title)).setText(subject.getName());
    }


}
