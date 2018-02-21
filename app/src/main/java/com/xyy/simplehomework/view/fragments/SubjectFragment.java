package com.xyy.simplehomework.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.view.App;

import java.util.List;

public class SubjectFragment extends DialogFragment {
    private static final String WEEK_ID = "week_id";
    private long week_id;
    private List<MySubject> subjectList;
    private OnFragmentInteractionListener mListener;

    public SubjectFragment() {
        setStyle(STYLE_NORMAL, R.style.BottomDialog);
    }

    public static SubjectFragment newInstance(long id) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle args = new Bundle();
        args.putLong(WEEK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            week_id = getArguments().getLong(WEEK_ID);
            subjectList = App.getInstance().getBoxStore().boxFor(MySubject.class).getAll();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SubjectAdapter adapter = new SubjectAdapter(R.layout.item_subject, subjectList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private static class SubjectAdapter extends BaseQuickAdapter<MySubject, BaseViewHolder> {

        public SubjectAdapter(int layoutResId, @Nullable List<MySubject> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MySubject item) {
            helper.setText(R.id.subject_name, item.getName());
        }
    }

}
