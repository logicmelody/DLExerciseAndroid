package com.dl.dlexerciseandroid.rightdrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.coordinatorlayout.CoordinatorLayoutActivity;

/**
 * Created by logicmelody on 2016/4/4.
 */
public class RightDrawerFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "com.dl.dlexerciseandroid.RightDrawerFragment";

    public interface OnRightDrawerListener {
        void onCloseRightDrawer();
    }

    private Context mContext;
    private OnRightDrawerListener mOnRightDrawerListener;

    private Button mCoordinatorLayoutButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mOnRightDrawerListener = (OnRightDrawerListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer_right, container, false);
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
        mCoordinatorLayoutButton = (Button) getView().findViewById(R.id.button_right_drawer_coordinator_layout);
    }

    private void setupViews() {
        mCoordinatorLayoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_right_drawer_coordinator_layout:
                startActivity(new Intent(mContext, CoordinatorLayoutActivity.class));
                mOnRightDrawerListener.onCloseRightDrawer();

                break;
        }
    }
}
