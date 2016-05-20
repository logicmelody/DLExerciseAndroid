package com.dl.dlsdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by logicmelody on 2016/5/19.
 */

// 如果要客製自己的view，一定要extend View這個class，或是一些Android提供的View，e.g. TextView or Button
public class DLButton extends View {

    private Context mContext;

    private boolean mShowDL;


    // 最少一定要提供這個constructor
    // 在xml中設定的所有屬性會被包在AttributeSet attrs中傳給這個constructor
    public DLButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        getAttributes(attrs);
        setupPaints();
    }

    private void getAttributes(AttributeSet attrs) {
        // 我們要用以下這種方式來取得xml中設定的值，這樣可以讓style也apply進去
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DLButton,
                0, 0);

        try {
            mShowDL = a.getBoolean(R.styleable.DLButton_showDL, false);

        } finally {
            a.recycle();
        }
    }

    private void setupPaints() {

    }

    // 用來畫view的外貌
    // What to draw, handled by Canvas
    // How to draw, handled by Paint.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    // 當view的size有改變的時候，就會call這個method
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    // 針對我們客製化的屬性，我們也要提供對應的set/get method讓view可以在runtime的時候改變
    // 要設計一個好的view，最好是每一個屬性都要提供對應的method來做動態設定
    public void setDLVisibility(boolean isShowDL) {
        mShowDL = isShowDL;

        // 當我們執行的code導致view的外觀有出現任何變化的時候，我們都應該call invalidate()來告訴系統這個view需要被redraw，
        // 並且利用requestLayout()來request a new layout
        invalidate();
        requestLayout();
    }

    public boolean isShowDLText() {
        return mShowDL;
    }
}
