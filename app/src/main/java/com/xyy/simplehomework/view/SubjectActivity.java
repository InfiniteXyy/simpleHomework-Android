package com.xyy.simplehomework.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import io.objectbox.BoxStore;
import me.relex.circleindicator.CircleIndicator;

public class SubjectActivity extends AppCompatActivity {
    public static final String SUBJECT_ID = "subject_id";
    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_subject);
        long subject_id = getIntent().getLongExtra(SUBJECT_ID, 0);
        BoxStore boxStore = App.getInstance().getBoxStore();
        subject = boxStore.boxFor(MySubject.class).get(subject_id);
        ((TextView) findViewById(R.id.title)).setText(subject.getName());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Card.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        Transition set = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_and_fade);
        viewPager.setPageMargin(-180);
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }


    public void finish(View view) {
        finish();
    }

    public static class Card extends Fragment {
        private String title;

        public static Card newInstance(int i) {
            Card fragment = new Card();
            switch (i) {
                case 0:
                    fragment.title = "课堂作业";
                    break;
                case 1:
                    fragment.title = "自学卡片";
                    break;
                case 2:
                    fragment.title = "今日推荐";
                    break;
            }
            return fragment;

        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.item_subject_card, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            view.findViewById(R.id.card).setOnClickListener((v) -> {

            });
            TextView textView = view.findViewById(R.id.title);
            textView.setText(title);
        }
    }
}
