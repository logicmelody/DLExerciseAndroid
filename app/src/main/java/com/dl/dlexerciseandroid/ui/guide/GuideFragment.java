package com.dl.dlexerciseandroid.ui.guide;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/6/5.
 */
public class GuideFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = GuideFragment.class.getName();

    private Context mContext;

    private Button mHighlightButton;
    private Button mCallGuideButton;

    private GuideView mGuideView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        //setupGuideView();
    }

    private void findViews() {
        mHighlightButton = (Button) getView().findViewById(R.id.button_guide_highlight);
        mCallGuideButton = (Button) getView().findViewById(R.id.button_guide_call_guide);
    }

    private void setupViews() {
        mHighlightButton.setOnClickListener(this);
        mCallGuideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guide_highlight:
                if (!hasGuideViewOnWindow()) {
                    return;
                }

                mGuideView.cleanUp();

                break;

            case R.id.button_guide_call_guide:
                if (hasGuideViewOnWindow()) {
                    return;
                }

                setupGuideView();

                break;
        }
    }

    private boolean hasGuideViewOnWindow() {
        return mGuideView != null && ViewCompat.isAttachedToWindow(mGuideView);
    }

    private void setupGuideView() {
        // TourGuide can only be setup after all the views is ready and obtain it's position/measurement
        // so when this is the 1st time TourGuide is being added,
        // else block will be executed, and ViewTreeObserver will make TourGuide setup process to be delayed until
        // everything is ready when this is run the 2nd or more times, if block will be executed
        if (ViewCompat.isAttachedToWindow(mHighlightButton)){
            addGuideView();

        } else {
            final ViewTreeObserver viewTreeObserver = mHighlightButton.getViewTreeObserver();

            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mHighlightButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    } else {
                        mHighlightButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    addGuideView();
                }
            });
        }
    }

    private void addGuideView() {
        mGuideView = new GuideView(mContext, mHighlightButton);

        ViewGroup contentArea =
                (ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);

        contentArea.addView(mGuideView, layoutParams);
    }
}


