package com.dl.dlexerciseandroid.ui.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.utility.utils.GeneralUtils;

public class GuideView extends FrameLayout {

    private Context mContext;

    private Bitmap mOverlayBitmap;
    private Canvas mOverlayCanvas;

    private Paint mEraserPaint;
    private Paint mOverlayPaint;

    private View mHighlightView;
    private RectF mRectF;


    public void setHighlightView(View highlightView) {
        this.mHighlightView = highlightView;
        invalidate();
    }

    public GuideView(Context context, View highlightView) {
        super(context);
        mContext = context;
        mHighlightView = highlightView;
        initialize();
    }

    private void initialize() {
        setWillNotDraw(false);
        initHighlightArea();

        mOverlayBitmap = Bitmap.createBitmap(mContext.getResources().getDisplayMetrics().widthPixels,
                mContext.getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mOverlayCanvas = new Canvas(mOverlayBitmap);

        mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOverlayPaint.setColor(0xcc000000);
        mOverlayPaint.setStyle(Paint.Style.FILL);

        mEraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEraserPaint.setStyle(Paint.Style.FILL);
        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private void initHighlightArea() {
        int[] pos = GeneralUtils.getViewLocationOnScreen(mHighlightView);
        mRectF = new RectF(pos[0], pos[1], pos[0] + mHighlightView.getWidth(), pos[1] + mHighlightView.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mOverlayBitmap.eraseColor(Color.TRANSPARENT);
        mOverlayCanvas.drawPaint(mOverlayPaint);
        mOverlayCanvas.drawRect(mRectF, mEraserPaint);

        canvas.drawBitmap(mOverlayBitmap, 0, 0, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOverlayCanvas.setBitmap(null);
        mOverlayBitmap = null;
    }

    public void cleanUp() {
        if (getParent() == null) {
            return;
        }

        ((ViewGroup) this.getParent()).removeView(this);
    }
}
