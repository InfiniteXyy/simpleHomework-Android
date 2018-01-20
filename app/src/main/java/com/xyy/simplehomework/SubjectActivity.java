package com.xyy.simplehomework;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;


public class SubjectActivity extends AppCompatActivity {

    public static final String SUBJECT_NAME = "subject_name";

    public static final String SUBJECT_IMAGE_ID = "subject_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        // 从 intent 获得需要的数据
        Intent intent = getIntent();
        final String subjectName = intent.getStringExtra(SUBJECT_NAME);
        final int subjectImageId = intent.getIntExtra(SUBJECT_IMAGE_ID, 0);
        // 设置 toolbar(title，img)
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击后问询删除这个文件
                Snackbar.make(v, "确定要删除吗", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                BoxStore boxStore = ((App) getApplication()).getBoxStore();
                                Box<MySubject> subjectBox = boxStore.boxFor(MySubject.class);
                                List<MySubject> subject = subjectBox.query().
                                        equal(MySubject_.name, subjectName).build().find();
                                subjectBox.remove(subject.get(0));
                                Toast.makeText(SubjectActivity.this, "已删除",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
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
