package com.dl.dlexerciseandroid.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/8/19.
 */

// 這個customized view，我們要用來做合成View
public class ValueSelectorView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    private static final int REPEAT_INTERVAL_MS = 100;

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

    private class SavedState extends View.BaseSavedState {

        public int value;


        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel source) {
            super(source);

            // 把值在restore的時候讀取回來
            value = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            // 將值save起來
            out.writeInt(value);
        }

        public final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

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
        getAttributes(attrs);
        initialize();
    }

    // 除了利用xml來產生View之外，也可以設定base style
    public ValueSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttributes(attrs);
        initialize();
    }

    private void getAttributes(AttributeSet attrs) {
        Log.d("danny", "ValueSelectorView getAttributes()");

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ValueSelectorView,
                0, 0);

        try {
            mCurrentValue = a.getInteger(R.styleable.ValueSelectorView_selectorValue, 0);

        } finally {
            // 讀取完值了以後，要記得把TypedArray a給回收
            a.recycle();
        }
    }

    private void initialize() {
        // 就算我們已經實作了onSaveInstanceState()跟onRestoreInstanceState()來將customized view的狀態保存起來
        // 1. 我們必須要在initialize()的時候call setSaveEnabled(true)
        // 2. 或是在xml中加入android:saveEnabled=“true
        // 這樣才會把恢復的功能打開
        setSaveEnabled(true);

        mHandler = new Handler();
        findViews();
        setupViews();
    }

    private void findViews() {
        // 我們在實作合成customized view的時候，會利用這種方法將已經定義好的xml layout assign給這個View，
        // 最後一個this的參數是關鍵
        // Note: 因為最後一個parameter我們有設定this，所以mRootView會是root view。
        //       若是沒有設定的話，這個return回來的view就單純只是這個inflated view的root
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_value_selector_view, this);

        mMinusButton = (ImageView) mRootView.findViewById(R.id.image_view_value_selector_view_minus_button);
        mPlusButton = (ImageView) mRootView.findViewById(R.id.image_view_value_selector_view_plus_button);
        mValueText = (TextView) mRootView.findViewById(R.id.text_view_value_selector_view_value);
    }

    private void setupViews() {
        refreshValueText();

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

    // Customized view若是想要在旋轉的時候可以保留原來View上面的狀態，需要把狀態用
    // onSaveInstanceState()存起來
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.value = mCurrentValue;

        return ss;
    }

    // 然後在onRestoreInstanceState()把狀態復原
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mCurrentValue = ss.value;

        // 這個customized view是一個合成view，記得要重新set值
        refreshValueText();
    }

    private void refreshValueText() {
        mValueText.setText(String.valueOf(mCurrentValue));
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
        refreshValueText();
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
        refreshValueText();
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
            refreshValueText();
        }
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMaxValue(int max) {
        mMaxValue = max;

        if (mCurrentValue > mMaxValue) {
            mCurrentValue = mMaxValue;
            refreshValueText();
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

        refreshValueText();
    }

    public int getValue() {
        return mCurrentValue;
    }
}
