package com.xyy.simplehomework.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import java.util.Arrays;

import io.objectbox.BoxStore;
import me.relex.circleindicator.CircleIndicator;

public class SubjectActivity extends AppCompatActivity {
    public static final String SUBJECT_ID = "subject_id";
    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        BoxStore boxStore = App.getInstance().getBoxStore();
        subject = boxStore.boxFor(MySubject.class).get(subject_id);
        ((TextView) findViewById(R.id.title)).setText(subject.getName());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new Card();
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        viewPager.setPageMargin(-180);
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }


    public void finish(View view) {
        finish();
    }

    public static class Card extends Fragment {
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.item_subject_card, container, false);
        }
    }
}
