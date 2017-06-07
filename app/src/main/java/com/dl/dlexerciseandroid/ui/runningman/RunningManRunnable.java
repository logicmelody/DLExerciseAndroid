package com.dl.dlexerciseandroid.ui.runningman;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/6/7.
 */

// 如何畫running man的動畫都定義在這個runnable中
public class RunningManRunnable implements Runnable {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private Resources mResources;
    private Canvas mCanvas;
    private Bitmap mRunningManBitmap;

    private volatile boolean mIsPlaying = true;
    private boolean mIsMoving = false;

    private float mRunningSpeedPerSecond = 500;
    private float mManXPos = 10;
    private float mManYPos = 10;

    private int mFrameWidth = 230;
    private int mFrameHeight = 274;
    private int mFrameCount = 8;
    private int mCurrentFrame = 0;

    private long mFps = 0;
    private long mTimeThisFrame = 0;
    private long mLastFrameChangeTime = 0;
    private int mFrameLengthInMillisecond = 50;

    // 要畫動畫的哪張圖
    private Rect mFrameToDraw = new Rect(0, 0, mFrameWidth, mFrameHeight);

    // 要畫在SurfaceView的哪邊
    private RectF mWhereToDraw = new RectF(mManXPos, mManYPos, mManXPos + mFrameWidth, mFrameHeight);


    public RunningManRunnable(Resources resources, SurfaceView surfaceView) {
        mResources = resources;
        mSurfaceView = surfaceView;
        mSurfaceHolder = mSurfaceView.getHolder();
        initialize();
    }

    private void initialize() {
        mRunningManBitmap = BitmapFactory.decodeResource(mResources, R.drawable.running_man);

        // 根據指定的width還有height，產生一個scale過後的bitmap
        mRunningManBitmap
                = Bitmap.createScaledBitmap(mRunningManBitmap, mFrameWidth * mFrameCount, mFrameHeight, false);
    }

    @Override
    public void run() {
        while (mIsPlaying) {
            long startFrameTime = System.currentTimeMillis();

            update();
            draw();

            mTimeThisFrame = System.currentTimeMillis() - startFrameTime;

            if (mTimeThisFrame >= 1) {
                mFps = 1000 / mTimeThisFrame;
            }
        }
    }

    private void update() {
        if (mIsMoving) {
            mManXPos += (mRunningSpeedPerSecond / mFps);

            // 如果x position超過SurfaceView的width，就讓y前進到下一個row
            if (mManXPos > mSurfaceView.getWidth()) {
                mManYPos += mFrameHeight;
                mManXPos = 10;
            }

            // 若是y已經到最後一個row了，就把y的位置回到最上面
            if (mManYPos + mFrameHeight > mSurfaceView.getHeight()) {
                mManYPos = 10;
            }
        }
    }

    private void draw() {
        if (!mSurfaceHolder.getSurface().isValid()) {
            return;
        }

        mCanvas = mSurfaceHolder.lockCanvas();

        mCanvas.drawColor(Color.WHITE);
        mWhereToDraw.set(mManXPos, mManYPos, mManXPos + mFrameWidth, mManYPos + mFrameHeight);

        manageCurrentFrame();

        // 把參考的Bitmap畫在canvas上
        mCanvas.drawBitmap(mRunningManBitmap, mFrameToDraw, mWhereToDraw, null);

        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }

    private void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (mIsMoving) {
            if (time > mLastFrameChangeTime + mFrameLengthInMillisecond) {
                mLastFrameChangeTime = time;
                mCurrentFrame++;

                if (mCurrentFrame >= mFrameCount) {
                    mCurrentFrame = 0;
                }
            }
        }

        mFrameToDraw.left = mCurrentFrame * mFrameWidth;
        mFrameToDraw.right = mFrameToDraw.left + mFrameWidth;
    }

    public void setPlaying(boolean isPlaying) {
        mIsPlaying = isPlaying;
    }

    public void changeMoving() {
        mIsMoving = !mIsMoving;
    }
}
