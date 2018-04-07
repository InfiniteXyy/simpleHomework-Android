package com.xyy.simplehomework.view.fragments.subject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;

import me.relex.circleindicator.CircleIndicator;

/**
 * Main fragment of subject activity
 */

public class SubjectMainFragment extends Fragment {
    private MySubject subject;

    public static SubjectMainFragment newInstance(MySubject subject) {
        SubjectMainFragment fragment = new SubjectMainFragment();
        fragment.subject = subject;
        fragment.setExitTransition(new Fade(Fade.OUT));
        fragment.setReenterTransition(new Fade(Fade.IN));
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Card.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.setPageMargin(-180);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
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
                CardFragment cardFragment = CardFragment.newInstance();
                Transition changeBound = new Slide();
                cardFragment.setEnterTransition(changeBound);
                cardFragment.setExitTransition(changeBound);
                cardFragment.setSharedElementEnterTransition(new ChangeBounds());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addSharedElement(view.findViewById(R.id.title), getString(R.string.title))
                        .replace(R.id.mainFragment, cardFragment)
                        .addToBackStack(null)
                        .commit();
            });
            TextView textView = view.findViewById(R.id.title);
            textView.setText(title);
        }
    }

}
