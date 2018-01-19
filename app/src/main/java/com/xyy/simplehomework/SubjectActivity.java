package com.xyy.simplehomework;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;


public class SubjectActivity extends AppCompatActivity {

    public static final String SUBJECT_NAME = "subject_name";

    public static final String SUBJECT_IMAGE_ID = "subject_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        // 从 intent 获得需要的数据
        Intent intent = getIntent();
        String subjectName = intent.getStringExtra(SUBJECT_NAME);
        int subjectImageId = intent.getIntExtra(SUBJECT_IMAGE_ID, 0);
        // 设置 toolbar(title，img)
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView subjectImageView = findViewById(R.id.subject_image_view);
        TextView subjectContentView = findViewById(R.id.subject_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // 开启默认的返回键
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(subjectName);
        Glide.with(this).load(subjectImageId).into(subjectImageView);
        subjectContentView.setText(genDemoTxt(subjectName));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String genDemoTxt(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(name);
        }
        return sb.toString();
    }
}
