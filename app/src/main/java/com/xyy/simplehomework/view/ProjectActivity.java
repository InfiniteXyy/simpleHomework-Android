package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivityProjectBinding;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.view.adapter.HomeworkAdapter;
import com.xyy.simplehomework.viewmodel.ProjectDetailViewModel;


public class ProjectActivity extends AppCompatActivity {
    public static final String PROJECT_ID = "PROJECT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_project);

        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        long id = getIntent().getLongExtra(PROJECT_ID, 0);
        ProjectDetailViewModel viewModel = new ProjectDetailViewModel(this, id);

        // get project
        MyProject project = viewModel.getProject();
        int color = getResources().getColor(project.subject.getTarget().colorId);
        toolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
        binding.setProject(project);

        RecyclerView recyclerView = findViewById(R.id.project_detail_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HomeworkAdapter adapter = new HomeworkAdapter(R.layout.item_homework_in_project_detail, project.homework);
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
