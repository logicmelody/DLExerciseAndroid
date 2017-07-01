package com.dl.dlexerciseandroid.ui.opencv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.NdkUtils;

/**
 * Created by logicmelody on 2017/7/1.
 */

public class OpenCVFragment extends Fragment {

    public static final String TAG = OpenCVFragment.class.getName();

    private Context mContext;

    private TextView mTestText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opencv, container, false);
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
        mTestText = (TextView) getView().findViewById(R.id.text_view_opencv_test_text);
    }

    private void setupViews() {
        mTestText.setText(NdkUtils.getTestStringFromJNI());
    }
}
