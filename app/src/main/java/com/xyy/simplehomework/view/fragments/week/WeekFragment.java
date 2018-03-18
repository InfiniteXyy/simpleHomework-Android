package com.xyy.simplehomework.view.fragments.week;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.helper.DateHelper;

import java.util.List;

/**
 * Created by xyy on 2018/1/27.
 */

public class WeekFragment extends Fragment implements WeekUIInteraction {
    public static final String TAG = "WeekFragment";
    int offsetY = 0;
    private boolean isText = false;
    private DetailFragment detailFragment;
    private TextFragment textFragment;
    private WeeksFragment weeksFragment;
    private WeekViewModel viewModel;
    private ImageView imgBtn;
    private ImageView weekBtn;
    private FloatingActionButton fab;
    private TextSwitcher title;
    private View shadow;
    private Animation fadeIn;
    private Animation fadeOut;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) fab.hide();
        else fab.show();
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(WeekViewModel.class);
        // second, init child fragments
        shadow = view.findViewById(R.id.shadow);
        title = view.findViewById(R.id.title);
        title.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(getContext());
                tv.setTextAppearance(getContext(), R.style.textSwitcher);
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        title.setInAnimation(fadeIn);
        title.setOutAnimation(fadeOut);
        title.setCurrentText("本周");

        offsetY = 0;
        detailFragment = new DetailFragment();
        detailFragment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offsetY += dy;
                if (offsetY <= 255) {
                    shadow.setAlpha((float) (offsetY / 255.0));
                }
            }
        });
        textFragment = TextFragment.newInstance(viewModel.getSubjectList(), DateHelper.getWeekIndex());
        weeksFragment = new WeeksFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment)
                .add(R.id.fragment_week, weeksFragment)
                .add(R.id.fragment_week, textFragment)
                .hide(textFragment)
                .hide(weeksFragment)
                .commit();

        imgBtn = view.findViewById(R.id.button);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isText) {
                    getChildFragmentManager().popBackStack();
                    imgBtn.setImageResource(R.drawable.ic_text_fields_black_24px);
                } else {
                    getChildFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                            .show(textFragment)
                            .hide(detailFragment)
                            .addToBackStack(null)
                            .commit();
                    imgBtn.setImageResource(R.drawable.ic_web_black_24px);
                }
                isText = !isText;
            }
        });

        weekBtn = view.findViewById(R.id.appBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.scale_in, R.anim.scale_out, R.anim.scale_in_re, R.anim.scale_out_re)
                        .show(weeksFragment)
                        .hide(isText ? textFragment : detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        //finally bind view model to sub fragments
        viewModel.getHomeworkLiveData().observe(this, new Observer<List<Homework>>() {
            @Override
            public void onChanged(@Nullable List<Homework> homework) {
                detailFragment.setHomeworkList(homework);
                textFragment.updateHomeworkList();
            }
        });
    }

    @Override
    public void onClickWeek(int weekIndex) {
        List<Homework> data = viewModel.getHomeworkData(weekIndex);
        detailFragment.setHomeworkList(data);
        textFragment.setWeek(weekIndex);
        if (weekIndex == DateHelper.getWeekIndex()) {
            title.setText("本周");
        } else {
            String titleText = "第" + DateHelper.num2cn[weekIndex + 1] + "周";
            title.setText(titleText);
        }
        getChildFragmentManager().popBackStack();
    }

    @Override
    public void needBtns(boolean need) {
        if (need) {
            fab.show();
            imgBtn.setVisibility(View.VISIBLE);
            weekBtn.setVisibility(View.VISIBLE);
            imgBtn.startAnimation(fadeIn);
            weekBtn.startAnimation(fadeIn);
        } else {
            fab.hide();
            imgBtn.setVisibility(View.GONE);
            weekBtn.setVisibility(View.GONE);
            imgBtn.startAnimation(fadeOut);
            weekBtn.startAnimation(fadeOut);
        }
    }

    @Override
    public void showAddDialog() {
        AddDialog.newInstance(null).show(getChildFragmentManager(), null);
    }

    @Override
    public void showAddDialog(MySubject subject) {
        AddDialog.newInstance(subject).show(getChildFragmentManager(), null);
    }

    @Override
    public List<MySubject> getSubjectList() {
        return viewModel.getSubjectList();
    }

    @Override
    public void putHomework(Homework homework) {
        viewModel.putHomework(homework);
    }

}
