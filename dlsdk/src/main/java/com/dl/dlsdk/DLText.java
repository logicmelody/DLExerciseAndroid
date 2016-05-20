package com.dl.dlsdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by logicmelody on 2016/5/20.
 */

// 如果要客製自己的view，我們也可以extend Android本來就有的一些view class，這樣可以讓開發變得比較簡單
public class DLText extends LinearLayout {

    private Context mContext;

    private TextView mMainTextView;
    private TextView mSubTextView;

    private String mMainText;
    private String mSubText;

    private int mMainTextSize;
    private int mSubTextSize;


    public DLText(Context context) {
        super(context);
    }

    public DLText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 一般只需要override這個constructor
    public DLText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        getAttributes(attrs);
        initialize();
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DLText,
                0, 0);
        try {
            mMainText = a.getString(R.styleable.DLText_mainText);
            mMainTextSize = a.getDimensionPixelSize(R.styleable.DLText_mainTextSize,
                    getResources().getDimensionPixelSize(R.dimen.text_size_dl_text_default_main_text));

            mSubText = a.getString(R.styleable.DLText_subText);
            mSubTextSize = a.getDimensionPixelSize(R.styleable.DLText_subTextSize,
                    getResources().getDimensionPixelSize(R.dimen.text_size_dl_text_default_sub_text));

        } finally {
            a.recycle();
        }
    }

    private void initialize() {
        // 如果只是把Android提供的一些view合成變成一個custom view，
        // 我們可以在custom view class中load我們事先定義好的layout resource
        // Note: 最後一個參數記得要assign this
        LayoutInflater.from(mContext).inflate(R.layout.layout_dl_text, this);

        findViews();
        setupViews();
    }

    private void findViews() {
        mMainTextView = (TextView) findViewById(R.id.text_view_dl_text_main_text);
        mSubTextView = (TextView) findViewById(R.id.text_view_dl_text_sub_text);
    }

    private void setupViews() {
        mMainTextView.setText(TextUtils.isEmpty(mMainText) ? "" : mMainText);
        mMainTextView.setTextSize(mMainTextSize);
        mSubTextView.setText(TextUtils.isEmpty(mSubText) ? "" : mSubText);
        mSubTextView.setTextSize(mSubTextSize);
    }
}
