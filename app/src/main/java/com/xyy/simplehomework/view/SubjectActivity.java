package com.xyy.simplehomework.view;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.subject.SubjectHomeworkFragment;
import com.xyy.simplehomework.view.fragments.subject.SubjectOverviewFragment;
import com.xyy.simplehomework.view.fragments.subject.SubjectSocietyFragment;

public class SubjectActivity extends AppCompatActivity {
    public static final String SUBJECT_ID = "subject_id";
    private final static String[] names = {
            "作业",
            "发现",
            "总览"
    };
    private SubjectHomeworkFragment homeworkFragment;
    private SubjectOverviewFragment overviewFragment;
    private SubjectSocietyFragment societyFragment;
    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        subject = App.getInstance().getBoxStore().boxFor(MySubject.class).get(subject_id);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set theme color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(subject.color);
        }
        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(subject.color);

        homeworkFragment = new SubjectHomeworkFragment();
        societyFragment = new SubjectSocietyFragment();
        overviewFragment = new SubjectOverviewFragment();
        // init viewPager
        ViewPager viewPager = findViewById(R.id.subjectMainPage);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return homeworkFragment;
                    case 1:
                        return societyFragment;
                    default:
                        return overviewFragment;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return names[position];
            }
        });
        homeworkFragment.setHomeworkList(subject.homework);
        // bind tabLayout to viewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
