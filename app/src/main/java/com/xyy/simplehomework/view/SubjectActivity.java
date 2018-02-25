package com.xyy.simplehomework.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.fragments.subject.SubjectHomeworkFragment;
import com.xyy.simplehomework.view.fragments.subject.SubjectOverviewFragment;
import com.xyy.simplehomework.view.fragments.subject.SubjectSocietyFragment;

public class SubjectActivity extends AppCompatActivity {

    public static final String SUBJECT_ID = "subject_id";
    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        subject = App.getInstance().getBoxStore().boxFor(MySubject.class).get(subject_id);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // set theme color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(subject.color);
        }
        toolbar.setBackgroundColor(subject.color);
        toolbar.setTitle(subject.getName());
        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(subject.color);

        // init viewPager
        ViewPager viewPager = findViewById(R.id.subjectMainPage);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TabFragment.values()[position].getFragment();
            }

            @Override
            public int getCount() {
                return TabFragment.values().length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TabFragment.values()[position].getTitle();
            }
        });
        // bind tabLayout to viewPager
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }

    private enum TabFragment {
        homework(R.id.homework, "作业", SubjectHomeworkFragment.class),
        society(R.id.society, "社区", SubjectSocietyFragment.class),
        Overview(R.id.overview, "总览", SubjectOverviewFragment.class);

        private final int tabId;
        private final String title;
        private final Class<? extends Fragment> clazz;
        private Fragment fragment;

        TabFragment(@IdRes int tabId, String title, Class<? extends Fragment> clazz) {
            this.tabId = tabId;
            this.clazz = clazz;
            this.title = title;
        }

        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }

        public Fragment getFragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }
}
