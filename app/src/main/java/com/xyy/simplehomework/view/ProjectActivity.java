package com.xyy.simplehomework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class ProjectActivity extends AppCompatActivity {

    public static final String PROJECT_ID = "project_id";

    public static final String SUBJECT_ID = "subject_id";

    private Box<MyProject> projectBox;
    private Box<MySubject> subjectBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        // 从 intent 获得需要的数据
        Intent intent = getIntent();
        final long project_id = intent.getLongExtra(PROJECT_ID, 0);
        final long subject_id = intent.getLongExtra(SUBJECT_ID, 0);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        projectBox = boxStore.boxFor(MyProject.class);
        subjectBox = boxStore.boxFor(MySubject.class);

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
                                projectBox.remove(project_id);
                                Toast.makeText(ProjectActivity.this, "已删除",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).show();
            }
        });
        TextView subjectContentView = findViewById(R.id.subject_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // 开启默认的返回键
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MySubject mySubject = subjectBox.get(subject_id);
        MyProject myProject = projectBox.get(project_id);

        collapsingToolbarLayout.setTitle(mySubject.name + " : " + myProject.detail);
        collapsingToolbarLayout.setBackgroundResource(mySubject.colorId);
        collapsingToolbarLayout.setContentScrimResource(mySubject.colorId);
        collapsingToolbarLayout.setStatusBarScrimResource(mySubject.colorId);
        subjectContentView.setText(genDemoTxt(mySubject.name));
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
