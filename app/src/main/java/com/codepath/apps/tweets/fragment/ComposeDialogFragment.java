package com.codepath.apps.tweets.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.tweets.R;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/23/17.
 */

public class ComposeDialogFragment extends DialogFragment {

    public interface ComposeListener {
        void onSave(String tweetBody);
    }

    @BindView(R.id.editTextCompose) EditText editTextCompose;
    @BindView(R.id.buttonSave) Button buttonSave;
    @BindView(R.id.buttonCancel) Button buttonCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose, container);
        bind(this, view);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "tweet saved");
                ComposeListener composeListener= (ComposeListener) getActivity();
                String composedTweetBody = editTextCompose.getText().toString();
                composeListener.onSave(composedTweetBody);
                dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "tweet cancelled");
                dismiss();
            }
        });
        return view;
    }
}
