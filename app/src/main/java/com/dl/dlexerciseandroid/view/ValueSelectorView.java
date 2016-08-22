package com.dl.dlexerciseandroid.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/8/19.
 */

// 這個customized view，我們要用來做合成View
public class ValueSelectorView extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {

    private static final int REPEAT_INTERVAL_MS = 100;

    // 控制長壓plus button，讓value可以自動往上加
    private class AutoPlus implements Runnable {

        @Override
        public void run() {
            if (!mIsPlusButtonPressed) {
                return;
            }

            plusValue();
            mHandler.postDelayed(new AutoPlus(), REPEAT_INTERVAL_MS);
        }
    }

    // 控制長壓minus button，讓value可以自動往下減
    private class AutoMinus implements Runnable {

        @Override
        public void run() {
            if (!mIsMinusButtonPressed) {
                return;
            }

            minusValue();
            mHandler.postDelayed(new AutoMinus(), REPEAT_INTERVAL_MS);
        }
    }

    private Context mContext;
    private Handler mHandler;

    private View mRootView;
    private ImageView mMinusButton;
    private ImageView mPlusButton;
    private TextView mValueText;

    private int mCurrentValue = 0;
    private int mMinValue = Integer.MIN_VALUE;
    private int mMaxValue = Integer.MAX_VALUE;

    private boolean mIsPlusButtonPressed = false;
    private boolean mIsMinusButtonPressed = false;


    // 在程式中動態生成View
    public ValueSelectorView(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    // 利用xml來產生View，通常最常使用的就是這個constructor
    public ValueSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();
    }

    // 除了利用xml來產生View之外，也可以設定base style
    public ValueSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize();
    }

    private void initialize() {
        mHandler = new Handler(mContext.getMainLooper());
        findViews();
        setupViews();
    }

    private void findViews() {
        // 我們在實作合成customized view的時候，會利用這種方法將已經定義好的xml layout assign給這個View
        // 最後一個this的參數是關鍵
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_value_selector_view, this);

        mMinusButton = (ImageView) mRootView.findViewById(R.id.image_view_value_selector_view_minus_button);
        mPlusButton = (ImageView) mRootView.findViewById(R.id.image_view_value_selector_view_plus_button);
        mValueText = (TextView) mRootView.findViewById(R.id.text_view_value_selector_view_value);
    }

    private void setupViews() {
        mMinusButton.setOnClickListener(this);
        mMinusButton.setOnLongClickListener(this);

        // 要有一個listener來偵測什麼時候user release minus button
        mMinusButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    mIsMinusButtonPressed = false;
                }

                return false;
            }
        });

        mPlusButton.setOnClickListener(this);
        mPlusButton.setOnLongClickListener(this);

        // 要有一個listener來偵測什麼時候user release plus button
        mPlusButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    mIsPlusButtonPressed = false;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_value_selector_view_plus_button:
                plusValue();
                break;

            case R.id.image_view_value_selector_view_minus_button:
                minusValue();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_value_selector_view_plus_button:
                autoPlusValue();
                break;

            case R.id.image_view_value_selector_view_minus_button:
                autoMinusValue();
                break;
        }

        return false;
    }

    private void plusValue() {
        if (mCurrentValue >= mMaxValue) {
            return;
        }

        mCurrentValue++;
        mValueText.setText(String.valueOf(mCurrentValue));
    }

    // 偵測到長壓event之後，就讓Handler重複執行AutoPlus這個Runnable object
    private void autoPlusValue() {
        mIsPlusButtonPressed = true;
        mHandler.post(new AutoPlus());
    }

    private void minusValue() {
        if (mCurrentValue <= mMinValue) {
            return;
        }

        mCurrentValue--;
        mValueText.setText(String.valueOf(mCurrentValue));
    }

    // 偵測到長壓event之後，就讓Handler重複執行AutoMinus這個Runnable object
    private void autoMinusValue() {
        mIsMinusButtonPressed = true;
        mHandler.post(new AutoMinus());
    }

    // 為了讓customized view有彈性，記得要定義一些public method
    public void setMinValue(int min) {
        mMinValue = min;

        if (mCurrentValue < mMinValue) {
            mCurrentValue = mMinValue;
            mValueText.setText(String.valueOf(mCurrentValue));
        }
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMaxValue(int max) {
        mMaxValue = max;

        if (mCurrentValue > mMaxValue) {
            mCurrentValue = mMaxValue;
            mValueText.setText(String.valueOf(mCurrentValue));
        }
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setValue(int newValue) {
        mCurrentValue = newValue;

        if (mCurrentValue >= mMaxValue) {
            mCurrentValue = mMaxValue;

        } else if (mCurrentValue <= mMinValue) {
            mCurrentValue = mMinValue;
        }

        mValueText.setText(String.valueOf(mCurrentValue));
    }

    public int getValue() {
        return mCurrentValue;
    }
}
