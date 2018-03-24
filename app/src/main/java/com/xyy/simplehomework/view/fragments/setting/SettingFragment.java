package com.xyy.simplehomework.view.fragments.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.MainActivity;
import com.xyy.simplehomework.view.fragments.MyFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xyy on 2018/1/29.
 */

public class SettingFragment extends MyFragment implements View.OnClickListener {
    public final static String TAG = "SettingFragment";
    public final static String THEME = "theme";
    private View last;
    private Animation fadeIn;
    private Animation fadeOut;
    private List<Integer> themes = Arrays.asList(R.style.Theme1,
            R.style.Theme2,
            R.style.Theme3);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int selectTheme = R.style.Theme1;
        if (getActivity() != null) {
            selectTheme = ((MainActivity) getActivity()).getThemeId();
        }
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
        last = buttons[themes.indexOf(selectTheme)].findViewById(R.id.done);
        last.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE).edit();
        switch (v.getId()) {
            case R.id.air:
                editor.putInt(THEME, themes.get(0));
                break;
            case R.id.tea:
                editor.putInt(THEME, themes.get(1));
                break;
            case R.id.brown:
                editor.putInt(THEME, themes.get(2));
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
