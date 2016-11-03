package com.dl.dlexerciseandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/10/29.
 */

public class ValueBar extends View {

    private int mMaxValue = 100;
    private int mCurrentValue = 0;

    private int mBarHeight;
    private int mCircleRadius;
    private int mSpaceAfterBar;
    private int mCircleTextSize;
    private int mLabelTextSize;
    private int mMaxValueTextSize;

    private int mBaseColor;
    private int mCircleTextColor;
    private int mFillColor;
    private int mLabelTextColor;
    private int mMaxValueTextColor;

    private String mLabelText;

    // 將view拆解，每一個部分都有一個專門的Paint object去畫
    private Paint mLabelPaint;
    private Paint mMaxValuePaint;
    private Paint mBarBasePaint;
    private Paint mBarFillPaint;
    private Paint mCirclePaint;
    private Paint mCurrentValuePaint;


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

        public final Parcelable.Creator<ValueBar.SavedState> CREATOR = new Parcelable.Creator<ValueBar.SavedState>() {
            public ValueBar.SavedState createFromParcel(Parcel in) {
                return new ValueBar.SavedState(in);
            }

            public ValueBar.SavedState[] newArray(int size) {
                return new ValueBar.SavedState[size];
            }
        };
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;

        // Invalidate the whole view. If the view is visible,
        // onDraw(android.graphics.Canvas) will be called at some point in the future.
        // This must be called from a UI thread. To call from a non-UI thread, call postInvalidate().
        // 告訴Android system這個view需要被redraw，所以onDraw()這個method之後會被執行
        invalidate();

        // requestLayout() means that the size of the view may have changed and needs to be remeasured,
        // which could impact the entire layout.
        // 以目前這個view來說，max value的label可能會改變整個view的大小，所以需要執行requestLayout()，讓整個view重新被measure
        requestLayout();
    }

    public void setValue(int value) {
        if (value > mMaxValue) {
            mCurrentValue = mMaxValue;

        } else if (value < 0) {
            mCurrentValue = 0;

        } else {
            mCurrentValue = value;
        }

        invalidate();
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public int getCurrentValue() {
        return mCurrentValue;
    }

    public ValueBar(Context context) {
        super(context);
        initialize();
    }

    // 主要會用到這一個constructor
    public ValueBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        initialize();
    }

    // 將constructor中的AttributeSet attrs傳入，使用TypedArray a將我們定義在xml中attribute的值取出來
    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ValueBar,
                0, 0);

        try {
            // R.styleable.ValueBar_barHeight這個id名字是自動產生的
            mCurrentValue = a.getInteger(R.styleable.ValueBar_barValue, 0);
            mBarHeight = a.getDimensionPixelSize(R.styleable.ValueBar_barHeight, 0);
            mCircleRadius = a.getDimensionPixelSize(R.styleable.ValueBar_circleRadius, 0);
            mSpaceAfterBar = a.getDimensionPixelSize(R.styleable.ValueBar_spaceAfterBar, 0);
            mCircleTextSize = a.getDimensionPixelSize(R.styleable.ValueBar_circleTextSize, 0);
            mLabelTextSize = a.getDimensionPixelSize(R.styleable.ValueBar_labelTextSize, 0);
            mMaxValueTextSize = a.getDimensionPixelSize(R.styleable.ValueBar_maxValueTextSize, 0);

            mBaseColor = a.getColor(R.styleable.ValueBar_baseColor, Color.BLACK);
            mCircleTextColor = a.getColor(R.styleable.ValueBar_circleTextColor, Color.BLACK);
            mFillColor = a.getColor(R.styleable.ValueBar_fillColor, Color.BLACK);
            mLabelTextColor = a.getColor(R.styleable.ValueBar_labelTextColor, Color.BLACK);
            mMaxValueTextColor = a.getColor(R.styleable.ValueBar_maxValueTextColor, Color.BLACK);

            mLabelText = a.getString(R.styleable.ValueBar_labelText);

        } finally {
            // 讀取完值了以後，要記得把TypedArray a給回收
            a.recycle();
        }
    }

    public ValueBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        initialize();
    }

    private void initialize() {
        setupPaints();
    }

    // 為了提升onDraw()時的效率，需要使用的一些Paint object我們在initialize()的時候就全部先建立好
    // 在onDraw()時有兩個重要的元件：
    // 1. Canvas: 要畫什麼(draw a line, draw a circle, etc)
    // 2. Paint: 要怎麼畫(color, font, text size, etc)
    private void setupPaints() {
        // 一般來說，如果是畫text的paint，需要設定
        // 1. Text size
        // 2. Text color
        // 3. Text align
        // 4. Typeface
        // All of the Paint objects are configured to use anti aliasing, so that everything will look smooth.
        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setTextSize(mLabelTextSize);
        mLabelPaint.setColor(mLabelTextColor);
        mLabelPaint.setTextAlign(Paint.Align.LEFT);
        mLabelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mMaxValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaxValuePaint.setTextSize(mMaxValueTextSize);
        mMaxValuePaint.setColor(mMaxValueTextColor);
        mMaxValuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mMaxValuePaint.setTextAlign(Paint.Align.RIGHT);

        // 一般來說，如果是畫shape的paint，只需要設定color就可以了
        mBarBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarBasePaint.setColor(mBaseColor);

        mBarFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarFillPaint.setColor(mFillColor);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mFillColor);

        mCurrentValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrentValuePaint.setTextSize(mCircleTextSize);
        mCurrentValuePaint.setColor(mCircleTextColor);
        mCurrentValuePaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 選轉後的順序：
     * onSaveInstanceState()
     * onRestoreInstanceState()
     * onMeasure()
     * onDraw()
     */
    // Customized view若是想要在旋轉的時候可以保留原來View上面的狀態，需要把狀態用
    // onSaveInstanceState()存起來
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("danny", "ValueBar onSaveInstanceState()");

        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.value = mCurrentValue;

        return ss;
    }

    // 然後在onRestoreInstanceState()把狀態復原
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d("danny", "ValueBar onRestoreInstanceState()");

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mCurrentValue = ss.value;
    }

    // Mode分為三種：
    // 1. EXACTLY: Parent view告訴我們這個customized view的準確大小
    // 2. AT_MOST:
    // 3. UNSPECIFIED: 沒有限制，沒有指定
    //
    // Measure的logic:
    // if mode is EXACTLY:
    //      We are being told what size we need to be, so just return the specSize (size from the measure spec)
    // else:
    //      Compute the desired size
    //
    // if mode is AT_MOST:
    //      return the lesser of desired size and specSize
    // else:
    //      return the desired size
    //
    // 但我們其實只要使用resolveSizeAndState()就可以幫我們完成以上的事情
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d("danny", "ValueBar onMeasure()");

        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    // 如何決定text的width and height：
    // width: 用Paint object去取text的bound，利用bound來得到text的width
    // height: 用getFontSpacing()來取得text的height
    private int measureWidth(int measureSpec) {
        // 左右padding
        int size = getPaddingLeft() + getPaddingRight();

        // Current value text
        // 我們用Paint object去取text的bound，利用bound來得到text的width
        Rect bounds = new Rect();
        mLabelPaint.getTextBounds(mLabelText, 0, mLabelText.length(), bounds);
        size += bounds.width();

        // Max value text
        bounds = new Rect();
        String maxValueText = String.valueOf(mMaxValue);
        mMaxValuePaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        size += bounds.width();

        // 最後只要把計算好的size傳入resolveSizeAndState()
        return resolveSizeAndState(size, measureSpec, 0);
    }

    // 計算height可以分為三部分：
    // 1. Current Value label
    // 2. Bar height, circle indicator, max value三者中的最大height值
    // 3. 上下的padding，padding的計算也是view的責任
    //
    // Height = (top and bottom padding) + (label height) +
    //          (Math.max(height of bar, circle indicator, and max value label))
    //
    // Bar跟circle indicator的height比較容易計算，只要直接拿出傳入的attribute值即可
    // 麻煩的是text height的計算，我們不能直接拿傳入的text size當作height，因為text會隨著字型還有大小寫而有不同的height
    // e.g. NO and no
    //
    // 要得到text的height，我們可以用paint object的getFontSpacing() method
    private int measureHeight(int measureSpec) {
        // 上下padding
        int size = getPaddingTop() + getPaddingBottom();

        // Current value text
        // getFontSpacing(): Return the recommended line spacing based on the current typeface and text size.
        // 用這個可以決定text的height
        size += mLabelPaint.getFontSpacing();

        // 第二部分的那一群物件
        float maxValueTextSpacing = mMaxValuePaint.getFontSpacing();
        size += Math.max(maxValueTextSpacing, Math.max(mBarHeight, mCircleRadius * 2));

        // 最後只要把計算好的size傳入resolveSizeAndState()
        return resolveSizeAndState(size, measureSpec, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("danny", "ValueBar onDraw()");

        drawLabel(canvas);
        drawBar(canvas);
        drawMaxValue(canvas);
    }

    // 畫current value text
    // 整個view的canvas左上角是(x, y) = (0, 0)，所以針對每一個要畫上去的元件，我們都必須去計算他的(x, y)
    private void drawLabel(Canvas canvas) {
        // 處理padding
        float x = getPaddingLeft();

        // The y coordinate marks the bottom of the text, so we need to factor in the height
        Rect bounds = new Rect();
        mLabelPaint.getTextBounds(mLabelText, 0, mLabelText.length(), bounds);
        float y = getPaddingTop() + bounds.height();

        canvas.drawText(mLabelText, x, y, mLabelPaint);
    }

    private void drawMaxValue(Canvas canvas) {
        String maxValue = String.valueOf(mMaxValue);

        Rect maxValueRect = new Rect();
        mMaxValuePaint.getTextBounds(maxValue, 0, maxValue.length(), maxValueRect);

        // 因為我們已經設定Paint.Align.RIGHT，所以要找到最右邊的x值
        float xPos = getWidth() - getPaddingRight();

        // Center vertical的位置 + text高度的一半
        float yPos = getBarCenter() + maxValueRect.height() / 2;

        canvas.drawText(maxValue, xPos, yPos, mMaxValuePaint);
    }

    private float getBarCenter() {
        // Position the bar slightly below the middle of the drawable area
        float barCenter = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2; // This is the center
        barCenter += getPaddingTop() + 0.1F * getHeight(); // Move it down a bit

        return barCenter;
    }

    private void drawBar(Canvas canvas) {
        String maxValueString = String.valueOf(mMaxValue);
        Rect maxValueRect = new Rect();
        mMaxValuePaint.getTextBounds(maxValueString, 0, maxValueString.length(), maxValueRect);

        // Based part
        float barLength =
                getWidth() - getPaddingRight() - getPaddingLeft() - mCircleRadius - maxValueRect.width() - mSpaceAfterBar;
        float barCenter = getBarCenter();
        float halfBarHeight = mBarHeight / 2;
        float top = barCenter - halfBarHeight;
        float bottom = barCenter + halfBarHeight;
        float left = getPaddingLeft();
        float right = getPaddingLeft() + barLength;

        RectF rect = new RectF(left, top, right, bottom);

        // The second and third parameters to drawRoundRect are the radii for drawing the corners of the rectangle.
        canvas.drawRoundRect(rect, halfBarHeight, halfBarHeight, mBarBasePaint);

        // Filled part
        float percentFilled = (float) mCurrentValue / (float) mMaxValue;
        float fillLength = barLength * percentFilled;
        float fillPosition = left + fillLength;

        RectF fillRect = new RectF(left, top, fillPosition, bottom);
        canvas.drawRoundRect(fillRect, halfBarHeight, halfBarHeight, mBarFillPaint);
        // First two parameters are x and y position
        canvas.drawCircle(fillPosition, barCenter, mCircleRadius, mCirclePaint);

        Rect bounds = new Rect();
        String valueString = String.valueOf(Math.round(mCurrentValue));
        mCurrentValuePaint.getTextBounds(valueString, 0, valueString.length(), bounds);

        float y = barCenter + (bounds.height() / 2);
        canvas.drawText(valueString, fillPosition, y, mCurrentValuePaint);
    }
}
