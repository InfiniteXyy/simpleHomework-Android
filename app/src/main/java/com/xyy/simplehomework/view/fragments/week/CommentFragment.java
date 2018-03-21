package com.xyy.simplehomework.view.fragments.week;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyy.simplehomework.BR;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.entity.Homework;
import com.xyy.simplehomework.entity.Note;

/**
 * Created by xyy on 2018/3/18.
 */

public class CommentFragment extends DialogFragment {
    private BaseQuickAdapter<Note, BaseViewHolder> adapter;
    private Homework homework;

    public CommentFragment() {
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    public static CommentFragment newInstance(Homework homework) {
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.homework = homework;
        return commentFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.close_btn).setOnClickListener((v) -> dismiss());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new BaseQuickAdapter<Note, BaseViewHolder>(R.layout.item_note, homework.notes) {
            @Override
            protected void convert(BaseViewHolder helper, Note item) {
                helper.setText(R.id.text, item.comment);
                helper.setText(R.id.index, Integer.toString(helper.getAdapterPosition() + 1));
                helper.addOnClickListener(R.id.delete);
            }
        };
        adapter.setOnItemChildClickListener(((adapter1, view1, position) -> {
            homework.notes.remove(position);
            adapter1.notifyDataSetChanged();
            TransitionManager.beginDelayedTransition(recyclerView);
        }));
        EditText editText = view.findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Note note = new Note();
                note.comment = editText.getText().toString();
                homework.notes.add(note);
                editText.setText("");
                adapter.notifyDataSetChanged();
                TransitionManager.beginDelayedTransition(recyclerView);
                return true;
            }
            return false;
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homework.notes.applyChangesToDb();
        homework.notifyPropertyChanged(BR.noteSize);
    }
}
