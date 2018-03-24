package com.xyy.simplehomework.view.fragments.week;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Fade;
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
    private DetailFragment detailFragment;
    private WeeksFragment weeksFragment;
    private WeekViewModel viewModel;
    private ImageView weekBtn;
    private FloatingActionButton fab;
    private TextSwitcher title;
    private View shadow;
    private Animation fadeIn;
    private Animation fadeOut;


    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        fab.setOnClickListener(v -> showAddDialog());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        setExitTransition(new Fade(Fade.OUT));
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(WeekViewModel.class);
        // second, init child fragments
        setFab(getActivity().findViewById(R.id.fab));
        fab.show();
        shadow = view.findViewById(R.id.shadow);
        title = view.findViewById(R.id.title);
        title.setFactory(() -> {
            TextView tv = new TextView(getContext());
            tv.setTextAppearance(getContext(), R.style.textSwitcher);
            tv.setGravity(Gravity.CENTER);
            return tv;
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
        weeksFragment = new WeeksFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_week, detailFragment)
                .add(R.id.fragment_week, weeksFragment)
                .hide(weeksFragment)
                .commit();

        weekBtn = view.findViewById(R.id.appBtn);
        weekBtn.setOnClickListener(v -> {
            if (detailFragment.isHidden()) {
                getChildFragmentManager().popBackStack();
                weekBtn.setImageResource(R.drawable.ic_apps_black_24px);
            } else {
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.scale_in, R.anim.scale_out, R.anim.scale_in_re, R.anim.scale_out_re)
                        .show(weeksFragment)
                        .hide(detailFragment)
                        .addToBackStack(null)
                        .commit();
                weekBtn.setImageResource(R.drawable.ic_back);
            }
        });

        //finally bind view model to sub fragments
        viewModel.getHomeworkLiveData().observe(this, homework ->
                detailFragment.setHomeworkList(homework));
    }

    @Override
    public void onClickWeek(int weekIndex, List<Homework> data) {
        detailFragment.setHomeworkList(data);
        weekBtn.setImageResource(R.drawable.ic_apps_black_24px);
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
        } else {
            fab.hide();
        }
    }

    @Override
    public void showAddDialog() {
        ((RecordFragment) getParentFragment()).showAddDialog(null);
    }

    @Override
    public void showAddDialog(MySubject subject) {
        ((RecordFragment) getParentFragment()).showAddDialog(subject);
    }

    @Override
    public List<MySubject> getSubjectList() {
        return viewModel.getSubjectList();
    }

    @Override
    public void putHomework(Homework homework) {
        viewModel.putHomework(homework);
    }

    @Override
    public WeekViewModel getViewModel() {
        return viewModel;
    }

}
