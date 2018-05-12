package com.dl.dlexerciseandroid.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/8/18.
 */

// This is the most basic building block of the UI and is also a fully functional class,
// though somewhat lacking in features (but that’s for us to implement).
public class LineChartView extends View {

    private Paint mBluePaint;
    private Paint mYellowPaint;

    private float[] mDataPoints;


    /**
     * Sets the y data points of the line chart. The data points
     * are assumed to be positive and equally spaced on the x-axis.
     * The line chart will be scaled so that the entire height of
     * the view is used.
     *
     * @param dataPoints y values of the line chart
     */
    public void setChartData(float[] dataPoints) {
        mDataPoints = dataPoints.clone();

        // 資料已經改變，所以需要通知View重新onDraw()
        invalidate();
    }

    public LineChartView(Context context) {
        super(context);
        initialize();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        // Unsupported Drawing Operations
        // setShadowLayer(): works with text only
        // Use a software layer type to force a view to be rendered in software.
        // If a view that is hardware accelerated (for instance, if your whole application is hardware accelerated),
        // is having rendering problems, this is an easy way to work around limitations of the hardware rendering pipeline.
        // 如果我們想要我們畫出來的曲線可以有陰影的效果，必須要將這個View設定在Software層render，不然會沒有效果
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 設定Paint物件，所有要畫東西的元件
        setupPaints();
    }

    private void setupPaints() {
        mBluePaint = new Paint();
        mBluePaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setColor(getResources().getColor(R.color.stroke_color_line_chart_view_line));
        mBluePaint.setStrokeWidth(10);

        // That will reduce the jagged edge look from the chart and make it a lot smoother.
        mBluePaint.setAntiAlias(true);

        // Now, everything we draw with the paint will generate a drop shadow as well.
        // The first argument is the blur radius that determines how blurry the shadow will be.
        // The higher number, the more blurrier the shadow. If you set the blur radius to 0,
        // the shadow layer will be removed.
        // The next two arguments is the drop shadow offset.
        // In this case the shadow will be 0 pixels to the right and 10 pixels lower than what we draw.
        // The last argument is the color of the shadow and here we’ve set it to black.
        mBluePaint.setShadowLayer(4, 0, 10, Color.BLACK);

        mYellowPaint = new Paint();
        mYellowPaint.setStyle(Paint.Style.FILL);
        mYellowPaint.setColor(getResources().getColor(R.color.stroke_color_line_chart_view_foreground_dot));
        mYellowPaint.setAntiAlias(true);
    }

    // 在這個View所在的rectangle進行draw的動作，我們可以將我們想要的東西畫在canvas上
    // (關鍵method！)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDataPoints == null || mDataPoints.length == 0) {
            return;
        }

        float maxValue = getMax(mDataPoints);
        Path path = new Path();

        // 設定Path的起始位置
        path.moveTo(getXPos(0, mDataPoints.length), getYPos(mDataPoints[0], maxValue));

        for (int i = 1 ; i < mDataPoints.length ; i++) {
            path.lineTo(getXPos(i, mDataPoints.length), getYPos(mDataPoints[i], maxValue));
        }

        canvas.drawPath(path, mBluePaint);

//        // 記得要考慮padding，要養成習慣
//        int left = getPaddingLeft();
//        int right = getWidth() - getPaddingRight();
//        int top = getPaddingTop();
//        int bottom = getHeight() - getPaddingBottom();
//
//        // Draw line
//        canvas.drawLine(left, top, right, bottom, mBluePaint);
    }

    private float getMax(float[] dataPoints) {
        if (dataPoints == null || dataPoints.length == 0) {
            return 0;
        }

        float max = Float.MIN_VALUE;

        for (float f : dataPoints) {
            if (f > max) {
                max = f;
            }
        }

        return max;
    }

    private float getXPos(int index, int totalPointCount) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float unit = width / (totalPointCount - 1);

        return getPaddingLeft() + index * unit;
    }

    private float getYPos(float value, float maxValue) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        value = (value / maxValue) * height; // scale to view height
        value = height - value; // invert
        value += getPaddingTop(); // offset for padding

        return value;
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        int left = getPaddingLeft();
        int right = getWidth() - getPaddingRight();
        int top = getPaddingTop();
        int bottom = getHeight() - getPaddingBottom();

        // Draw circle
        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, 30, mYellowPaint);
    }
}
