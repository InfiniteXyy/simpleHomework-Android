package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivityHomeworkBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.view.adapter.HomeworkAdapter;
import com.xyy.simplehomework.viewmodel.HomeworkDetailViewModel;

import java.util.Collections;

public class HomeworkActivity extends AppCompatActivity {
    public static final String HOMEWORK_ID = "homework";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeworkBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_homework);

        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        long id = getIntent().getLongExtra(HOMEWORK_ID, 0);
        HomeworkDetailViewModel viewModel = new HomeworkDetailViewModel(this, id);

        // get Homework
        Homework homework = viewModel.getHomework();
        binding.setHomework(homework);
        binding.setProject(homework.project.getTarget());

        RecyclerView recyclerView = findViewById(R.id.project_detail_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HomeworkAdapter adapter = new HomeworkAdapter(R.layout.item_homework_in_project_detail, Collections.singletonList(viewModel.getHomework()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
