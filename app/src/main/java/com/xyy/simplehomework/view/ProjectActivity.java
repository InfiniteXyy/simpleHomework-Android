package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivityProjectBinding;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MyProject;
import com.xyy.simplehomework.entity.Week;
import com.xyy.simplehomework.view.helper.TitleSwitcher;
import com.xyy.simplehomework.viewmodel.ProjectDetailViewModel;

import org.w3c.dom.Text;


public class ProjectActivity extends AppCompatActivity {
    public static final String PROJECT_ID = "PROJECT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_project);

        long id = getIntent().getLongExtra(PROJECT_ID, 0);
        ProjectDetailViewModel viewModel = new ProjectDetailViewModel(this, id);
        binding.setProject(viewModel.getProject());
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
