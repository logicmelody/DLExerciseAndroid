package com.dl.dlexerciseandroid.features.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utils.Utils;

public class OverlayHoleView extends FrameLayout {

    private Context mContext;

    private Bitmap mOverlayBitmap;
    private Canvas mOverlayCanvas;

    private Paint mEraserPaint;
    private Paint mOverlayPaint;

    private View mHighlightView;
    private RectF mRectF;


    public void setHighlightView(View highlightView) {
        mHighlightView = highlightView;
        invalidate();
    }

    public OverlayHoleView(@NonNull Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public OverlayHoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();
    }

    public OverlayHoleView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize();
    }

    private void initialize() {
        // 在ViewGroup中，如果要在canvas上畫東西，要加上這行，如果是普通一般的View不用加
        setWillNotDraw(false);

        mOverlayBitmap = Bitmap.createBitmap(mContext.getResources().getDisplayMetrics().widthPixels,
                mContext.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mOverlayCanvas = new Canvas(mOverlayBitmap);

        mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOverlayPaint.setColor(mContext.getResources().getColor(R.color.background_guide_overlay));
        mOverlayPaint.setStyle(Paint.Style.FILL);

        mEraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEraserPaint.setStyle(Paint.Style.FILL);
        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mHighlightView == null) {
            return;
        }

        setHighlightArea();

        mOverlayBitmap.eraseColor(Color.TRANSPARENT);
        mOverlayCanvas.drawPaint(mOverlayPaint);
        mOverlayCanvas.drawRect(mRectF, mEraserPaint);

        canvas.drawBitmap(mOverlayBitmap, 0, 0, null);
    }

    private void setHighlightArea() {
        int[] pos = Utils.getViewLocationOnScreen(mHighlightView);
        mRectF = new RectF(pos[0], pos[1], pos[0] + mHighlightView.getWidth(), pos[1] + mHighlightView.getHeight());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOverlayCanvas.setBitmap(null);
        mOverlayBitmap = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 可以把touch event往下傳到highlight view，所以點擊highlight view可以觸發事件，
        // 但是點擊highlight view以外的地方沒有作用
        return mHighlightView == null ? super.dispatchTouchEvent(ev) : !isWithinHighlightView(ev);
    }

    private boolean isWithinHighlightView(MotionEvent ev) {
        int[] pos = Utils.getViewLocationOnScreen(mHighlightView);

        return ev.getRawY() >= pos[1] &&
                ev.getRawY() <= (pos[1] + mHighlightView.getHeight()) &&
                ev.getRawX() >= pos[0] &&
                ev.getRawX() <= (pos[0] + mHighlightView.getWidth());
    }
}
