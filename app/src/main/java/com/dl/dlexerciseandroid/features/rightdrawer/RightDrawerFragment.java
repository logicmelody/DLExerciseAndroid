package com.dl.dlexerciseandroid.features.rightdrawer;

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
import com.dl.dlexerciseandroid.features.bottomnavigationview.BottomNavigationViewActivity;
import com.dl.dlexerciseandroid.features.bubbletext.BubbleTextActivity;
import com.dl.dlexerciseandroid.features.coordinatorlayout.CoordinatorLayoutActivity;
import com.dl.dlexerciseandroid.features.espressotest.EspressoTestActivity;
import com.dl.dlexerciseandroid.features.guide.GuideActivity;
import com.dl.dlexerciseandroid.features.instagramapi.main.InstagramMainActivity;
import com.dl.dlexerciseandroid.features.mininavigationdrawer.main.MiniNavigationDrawerActivity;
import com.dl.dlexerciseandroid.features.moviesearcher.MovieSearcherActivity;
import com.dl.dlexerciseandroid.features.previewcamera.PreviewCameraActivity;
import com.dl.dlexerciseandroid.features.runningman.RunningManActivity;

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
    private Button mInstagramApiButton;
    private Button mGuideButton;


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
        mInstagramApiButton = (Button) getView().findViewById(R.id.button_right_drawer_instagram_api);
        mGuideButton = (Button) getView().findViewById(R.id.button_right_drawer_guide);
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
        mInstagramApiButton.setOnClickListener(this);
        mGuideButton.setOnClickListener(this);
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

            case R.id.button_right_drawer_instagram_api:
                startActivity(new Intent(mContext, InstagramMainActivity.class));
                break;

            case R.id.button_right_drawer_guide:
                startActivity(new Intent(mContext, GuideActivity.class));
                break;
        }

        mOnRightDrawerListener.onCloseRightDrawer();
    }
}
