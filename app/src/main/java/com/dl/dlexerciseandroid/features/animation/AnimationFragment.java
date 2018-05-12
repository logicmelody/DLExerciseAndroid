package com.dl.dlexerciseandroid.features.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;


/**
 * Created by logicmelody on 2016/4/8.
 */
public class AnimationFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = AnimationFragment.class.getName();

    private Context mContext;

    private ViewGroup mFlipImageContainer;
    private ImageView mFlipImageFront;
    private ImageView mFlipImageBack;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private Handler mHandler;
    private FlipImageRunnable mFlipImageRunnable;

    private boolean mIsFlipBackVisible = false;


    private class FlipImageRunnable implements Runnable {

        @Override
        public void run() {
            if (!mIsFlipBackVisible) {
                mSetRightOut.setTarget(mFlipImageFront);
                mSetLeftIn.setTarget(mFlipImageBack);
                mSetRightOut.start();
                mSetLeftIn.start();

                mIsFlipBackVisible = true;

            } else {
                mSetRightOut.setTarget(mFlipImageBack);
                mSetLeftIn.setTarget(mFlipImageFront);
                mSetRightOut.start();
                mSetLeftIn.start();

                mIsFlipBackVisible = false;
            }

            mHandler.postDelayed(mFlipImageRunnable, 2000);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        mHandler = new Handler();
        mFlipImageRunnable = new FlipImageRunnable();

        findViews();
        setupViews();
        setupAnimators();
    }

    private void findViews() {
        mFlipImageContainer = (ViewGroup) getView().findViewById(R.id.view_group_animation_flip_image_view_container);
        mFlipImageFront = (ImageView) getView().findViewById(R.id.image_view_animation_flip_front);
        mFlipImageBack = (ImageView) getView().findViewById(R.id.image_view_animation_flip_back);
    }

    private void setupViews() {
        mFlipImageContainer.setOnClickListener(this);
    }

    private void setupAnimators() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.flip_right_out);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.flip_left_in);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_group_animation_flip_image_view_container:
                mHandler.post(mFlipImageRunnable);

                break;
        }
    }

    @Override
    public void onDestroy() {
        // 記得要把runnable的資源release掉，這邊因為是使用UI thread而不是自己new出來的HandlerThread，所以只需要將runnable remove掉即可
        mHandler.removeCallbacks(mFlipImageRunnable);

        super.onDestroy();
    }
}