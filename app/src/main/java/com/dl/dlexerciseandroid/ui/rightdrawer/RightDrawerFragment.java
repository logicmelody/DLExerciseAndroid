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
import com.dl.dlexerciseandroid.ui.bottomnavigationview.BottomNavigationViewActivity;
import com.dl.dlexerciseandroid.ui.bubbletext.BubbleTextActivity;
import com.dl.dlexerciseandroid.ui.coordinatorlayout.CoordinatorLayoutActivity;
import com.dl.dlexerciseandroid.ui.espressotest.EspressoTestActivity;
import com.dl.dlexerciseandroid.ui.mininavigationdrawer.main.MiniNavigationDrawerActivity;
import com.dl.dlexerciseandroid.ui.moviesearcher.MovieSearcherActivity;
import com.dl.dlexerciseandroid.ui.previewcamera.PreviewCameraActivity;
import com.dl.dlexerciseandroid.ui.runningman.RunningManActivity;

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
    private Button mMiniNavigationDrawerButton;
    private Button mBottomNavigationViewButton;
    private Button mPreviewCameraButton;
    private Button mRunningManButton;
    private Button mBubbleTextButton;


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
        mMiniNavigationDrawerButton = (Button) getView().findViewById(R.id.button_right_drawer_mini_navigation_drawer);
        mBottomNavigationViewButton = (Button) getView().findViewById(R.id.button_right_drawer_bottom_navigation_view);
        mPreviewCameraButton = (Button) getView().findViewById(R.id.button_right_drawer_preview_camera);
        mRunningManButton = (Button) getView().findViewById(R.id.button_right_drawer_running_man);
        mBubbleTextButton = (Button) getView().findViewById(R.id.button_right_drawer_bubble_text);
    }

    private void setupViews() {
        mCoordinatorLayoutButton.setOnClickListener(this);
        mMovieSearcherButton.setOnClickListener(this);
        mEspressoTestButton.setOnClickListener(this);
        mMiniNavigationDrawerButton.setOnClickListener(this);
        mBottomNavigationViewButton.setOnClickListener(this);
        mPreviewCameraButton.setOnClickListener(this);
        mRunningManButton.setOnClickListener(this);
        mBubbleTextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_right_drawer_coordinator_layout:
                startActivity(new Intent(mContext, CoordinatorLayoutActivity.class));
                break;

            case R.id.button_right_drawer_movie_searcher:
                startActivity(new Intent(mContext, MovieSearcherActivity.class));
                break;

            case R.id.button_right_drawer_espresso_test:
                startActivity(new Intent(mContext, EspressoTestActivity.class));
                break;

            case R.id.button_right_drawer_mini_navigation_drawer:
                startActivity(new Intent(mContext, MiniNavigationDrawerActivity.class));
                break;

            case R.id.button_right_drawer_bottom_navigation_view:
                startActivity(new Intent(mContext, BottomNavigationViewActivity.class));
                break;

            case R.id.button_right_drawer_preview_camera:
                startActivity(new Intent(mContext, PreviewCameraActivity.class));
                break;

            case R.id.button_right_drawer_running_man:
                startActivity(new Intent(mContext, RunningManActivity.class));
                break;

            case R.id.button_right_drawer_bubble_text:
                startActivity(new Intent(mContext, BubbleTextActivity.class));
                break;
        }

        mOnRightDrawerListener.onCloseRightDrawer();
    }
}
