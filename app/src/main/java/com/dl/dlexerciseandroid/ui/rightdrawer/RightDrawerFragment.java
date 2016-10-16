package com.dl.dlexerciseandroid.ui.rightdrawer;

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
import com.dl.dlexerciseandroid.ui.coordinatorlayout.CoordinatorLayoutActivity;
import com.dl.dlexerciseandroid.ui.espressotest.EspressoTestActivity;
import com.dl.dlexerciseandroid.ui.moviesearcher.MovieSearcherActivity;

/**
 * Created by logicmelody on 2016/4/4.
 */
public class RightDrawerFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = RightDrawerFragment.class.getName();

    public interface OnRightDrawerListener {
        void onCloseRightDrawer();
    }

    private Context mContext;
    private OnRightDrawerListener mOnRightDrawerListener;

    private Button mCoordinatorLayoutButton;
    private Button mMovieSearcherButton;
    private Button mEspressoTestButton;


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
        mMovieSearcherButton = (Button) getView().findViewById(R.id.button_right_drawer_movie_searcher);
        mEspressoTestButton = (Button) getView().findViewById(R.id.button_right_drawer_espresso_test);
    }

    private void setupViews() {
        mCoordinatorLayoutButton.setOnClickListener(this);
        mMovieSearcherButton.setOnClickListener(this);
        mEspressoTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_right_drawer_coordinator_layout:
                startActivity(new Intent(mContext, CoordinatorLayoutActivity.class));
                mOnRightDrawerListener.onCloseRightDrawer();

                break;

            case R.id.button_right_drawer_movie_searcher:
                startActivity(new Intent(mContext, MovieSearcherActivity.class));
                mOnRightDrawerListener.onCloseRightDrawer();

                break;

            case R.id.button_right_drawer_espresso_test:
                startActivity(new Intent(mContext, EspressoTestActivity.class));
                mOnRightDrawerListener.onCloseRightDrawer();

                break;
        }
    }
}
