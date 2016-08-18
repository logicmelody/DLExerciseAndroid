package com.dl.dlexerciseandroid.ui.customizedview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.view.DurationTextView;

/**
 * Created by logicmelody on 2016/8/18.
 */
public class CustomizedViewFragment extends Fragment {

    public static final String TAG = CustomizedViewFragment.class.getName();

    private Context mContext;

    private DurationTextView mDuration;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customized_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupDuration();
    }

    private void findViews() {
        mDuration = (DurationTextView) getView().findViewById(R.id.duration_text_view_duration);
    }

    private void setupDuration() {
        // About 2 hrs 48 mins
        mDuration.setDuration(10000);
    }
}
