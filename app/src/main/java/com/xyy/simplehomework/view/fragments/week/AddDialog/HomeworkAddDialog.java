package com.xyy.simplehomework.view.fragments.week.AddDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import me.relex.circleindicator.CircleIndicator;


public class HomeworkAddDialog extends DialogFragment {
    private static final String TAG = "HomeworkAddDialog";
    private AddDialogInteraction mListener;
    public HomeworkAddDialog() {
        // Required empty public constructor
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    public String getTitle() {
        return "测试";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_homework_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = view.findViewById(R.id.view_page);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return AddFragments.values()[position].getFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (ProjectViewModel.getInstance() instanceof AddDialogInteraction) {
            mListener = ProjectViewModel.getInstance();
        } else {
            throw new RuntimeException("The parent fragment must implement AddDialogInteraction");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "dialog onDetach: ");
        super.onDetach();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d(TAG, "dialog onDismiss: ");
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        AddFragments.onDestroy();
    }

    private enum AddFragments {
        main(AddMainFragment.class),
        second(AddSecondFragment.class);
        private final Class<? extends Fragment> clazz;
        private Fragment fragment;

        AddFragments(Class<? extends Fragment> clazz) {
            this.clazz = clazz;
        }

        public static void onDestroy() {
            for (AddFragments fragment : values()) {
                fragment.fragment = null;
            }
        }

        @NonNull
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
    }

    public interface AddDialogInteraction {
        void addHomework(Homework homework);
    }
}
