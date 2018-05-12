package com.dl.dlexerciseandroid.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by dannylin on 2016/10/4.
 */
public class SoundSettingView extends RelativeLayout {

    public interface OnResetSoundListener {
        void onResetSound(View v);
    }

    private Context mContext;

    private View mRootView;

    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private TextView mResetButton;

    private String mTitle;
    private String mSubtitle;

    private OnResetSoundListener mOnResetSoundListener;


    public void setTitle(String title) {
        mTitleTextView.setText(title);
        invalidate();
    }

    public void setSubtitle(String subtitle) {
        mSubtitleTextView.setText(subtitle);
        invalidate();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setOnClickResetButtonListener(OnResetSoundListener listener) {
        mOnResetSoundListener = listener;

        mResetButton.setVisibility(VISIBLE);
        mResetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnResetSoundListener.onResetSound(SoundSettingView.this);
            }
        });
    }

    public SoundSettingView(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public SoundSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttributes(attrs);
        initialize();
    }

    public SoundSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttributes(attrs);
        initialize();
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SoundSettingView,
                0, 0);

        try {
            mTitle = a.getString(R.styleable.SoundSettingView_title);
            mSubtitle = a.getString(R.styleable.SoundSettingView_subtitle);

        } finally {
            a.recycle();
        }
    }

    private void initialize() {
        setupRootView();
        findViews();
        setupViews();
    }

    private void setupRootView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_sound_setting_view, this);
    }

    private void findViews() {
        mTitleTextView = (TextView) mRootView.findViewById(R.id.textview_sound_setting_view_title);
        mSubtitleTextView = (TextView) mRootView.findViewById(R.id.textview_sound_setting_view_subtitle);
        mResetButton = (TextView) mRootView.findViewById(R.id.textview_sound_setting_view_reset_button);
    }

    private void setupViews() {
        mTitleTextView.setText(TextUtils.isEmpty(mTitle) ? "" : mTitle);
        mSubtitleTextView.setText(TextUtils.isEmpty(mSubtitle) ? "" : mSubtitle);
    }
}
