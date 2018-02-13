package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivityProjectBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.adapter.SmallHomeworkAdapter;
import com.xyy.simplehomework.viewmodel.ProjectDetailViewModel;

import java.util.List;


public class ProjectActivity extends AppCompatActivity {
    public static final String PROJECT_ID = "PROJECT_ID";
    private List<Homework> homeworkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_project);

        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        long id = getIntent().getLongExtra(PROJECT_ID, 0);
        final ProjectDetailViewModel viewModel = new ProjectDetailViewModel(this, id);

        // get project & subject
        MyProject project = viewModel.getProject();
        MySubject subject = viewModel.getSubject();

        // set color
        int color = viewModel.getColor();
        toolbar.setBackgroundColor(color);
        collapsingToolbarLayout.setBackgroundColor(color);
        collapsingToolbarLayout.setContentScrimColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
        // bind data to view
        binding.setProject(project);
        binding.setSubject(subject);

        RecyclerView recyclerView = findViewById(R.id.project_detail_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        homeworkList = viewModel.getHomeworkList();
        SmallHomeworkAdapter adapter = new SmallHomeworkAdapter(R.layout.item_homework_in_project_detail, homeworkList);

        // set drag feature
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // very important, decide which item to drag
        adapter.enableDragItem(itemTouchHelper);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        });

        recyclerView.setAdapter(adapter);

        // demo used button
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.test();
                homeworkList.get(0).setStatus(3010);
            }
        });
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
