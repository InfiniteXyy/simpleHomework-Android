package com.xyy.simplehomework.view.fragments.semester;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.xyy.simplehomework.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/1/29.
 */

public class SemesterFragment extends Fragment implements View.OnClickListener{
    public final static String TAG = "SemesterFragment";
    private View last;
    private Animation fadeIn;
    private Animation fadeOut;
    private List<Integer> themes = Arrays.asList(R.style.Theme1,
            R.style.Theme2,
            R.style.Theme3);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_semester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int selectColor = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE).getInt("theme", themes.get(0));
        int colors[] = {
                getResources().getColor(R.color.japanAir),
                getResources().getColor(R.color.japanTea),
                getResources().getColor(R.color.japanBrown)
        };
        fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        fadeIn.setDuration(100);
        fadeOut.setDuration(100);

        View buttons[] = {
                view.findViewById(R.id.air),
                view.findViewById(R.id.tea),
                view.findViewById(R.id.brown)
        };
        for (int i = 0; i < 3; i++) {
            ImageView background = buttons[i].findViewById(R.id.circle);
            background.setColorFilter(colors[i]);
            buttons[i].setOnClickListener(this);
        }
        last = buttons[themes.indexOf(selectColor)].findViewById(R.id.done);
        last.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE).edit();
        switch (v.getId()) {
            case R.id.air:
                editor.putInt("theme", themes.get(0));
                break;
            case R.id.tea:
                editor.putInt("theme", themes.get(1));
                break;
            case R.id.brown:
                editor.putInt("theme", themes.get(2));
                break;
        }

        View done = v.findViewById(R.id.done);
        if (last != done) {
            if (last != null) {
                last.setVisibility(View.GONE);
                last.startAnimation(fadeOut);
            }
            done.setVisibility(View.VISIBLE);
            done.startAnimation(fadeIn);
            last = done;
        }

        editor.apply();
        Toast.makeText(getContext(), "下次启动时生效", Toast.LENGTH_SHORT).show();
    }
}
