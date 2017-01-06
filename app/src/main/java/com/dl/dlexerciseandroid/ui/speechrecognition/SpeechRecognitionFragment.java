package com.dl.dlexerciseandroid.ui.speechrecognition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by dannylin on 2017/1/6.
 */

public class SpeechRecognitionFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SpeechRecognitionFragment.class.getName();

    private Context mContext;

    private ImageView mMicButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speech_recognition, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {
        mMicButton = (ImageView) getView().findViewById(R.id.image_view_speech_recognition_mic_button);
    }

    private void setupViews() {
        mMicButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_speech_recognition_mic_button:
                break;
        }
    }
}
