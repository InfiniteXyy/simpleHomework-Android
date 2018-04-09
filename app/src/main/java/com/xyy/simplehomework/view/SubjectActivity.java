package com.xyy.simplehomework.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.subject.SubjectMainFragment;

import io.objectbox.BoxStore;

/**
 * subject main activity
 * contains {@link SubjectMainFragment}
 */

public class SubjectActivity extends AppCompatActivity {
    public static final String SUBJECT_ID = "subject_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        BoxStore boxStore = App.getInstance().getBoxStore();
        MySubject subject = boxStore.boxFor(MySubject.class).get(subject_id);
        ((TextView) findViewById(R.id.title)).setText(subject.getName());
        SubjectMainFragment mainFragment = SubjectMainFragment.newInstance(subject);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();
    }

    public void finish(View v) {
        super.finish();
    }
}
