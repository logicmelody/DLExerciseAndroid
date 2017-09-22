package com.dl.dlexerciseandroid.ui.guide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.R;

public class GuideView extends FrameLayout implements View.OnClickListener {

    public interface OnGuideViewListener {
        void onClickIKnow();
    }

    private Context mContext;

    private View mHighlightView;
    private OverlayHoleView mOverlayHoleView;
    private Button mIKnowButton;

    private OnGuideViewListener mOnGuideViewListener;


    public GuideView(@NonNull Context context, View highlightView, OnGuideViewListener listener) {
        super(context);
        mContext = context;
        mHighlightView = highlightView;
        mOnGuideViewListener = listener;
        initialize();
        setupButtons();
        setHighlightView();
    }

    private void initialize() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_guide_view, this);

        mOverlayHoleView = (OverlayHoleView) findViewById(R.id.overlay_hole_view_guide_overlay);
        mIKnowButton = (Button) findViewById(R.id.button_guide_i_know);
    }

    private void setupButtons() {
        mIKnowButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guide_i_know:
                if (mOnGuideViewListener != null) {
                    mOnGuideViewListener.onClickIKnow();
                }

                removeSelf();

                break;
        }
    }

    public void removeSelf() {
        if (getParent() == null) {
            return;
        }

        ((ViewGroup) this.getParent()).removeView(this);
    }

    public void setHighlightView() {
        mOverlayHoleView.setHighlightView(mHighlightView);
    }
}
