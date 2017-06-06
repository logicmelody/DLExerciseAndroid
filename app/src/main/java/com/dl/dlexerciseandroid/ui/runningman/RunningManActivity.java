package com.dl.dlexerciseandroid.ui.runningman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.dl.dlexerciseandroid.R;

public class RunningManActivity extends AppCompatActivity {

    private static final String THREAD_RUNNING_MAN = "com.dl.dlexerciseandroid.THREAD_RUNNING_MAN";

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    // 用一條HandlerThread跟Handler來處理畫running man的動畫
    private HandlerThread mRunningManThread;
    private Handler mRunningMainHandler;
    private RunningManRunnable mRunningManRunnable;


    // 如何畫running man的動畫都定義在這個runnable中
    private class RunningManRunnable implements Runnable {

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


        public RunningManRunnable() {
            initialize();
        }

        private void initialize() {
            mRunningManBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.running_man);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_man);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupSurfaceView();
        setupRunningManThread();
    }

    private void findViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_running_main);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setTitle(getString(R.string.all_running_man));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupSurfaceView() {
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if (mRunningManRunnable == null) {
                            break;
                        }

                        mRunningManRunnable.changeMoving();

                        break;
                }

                return true;
            }
        });

        mSurfaceHolder = mSurfaceView.getHolder();
    }

    private void setupRunningManThread() {
        mRunningManThread = new HandlerThread(THREAD_RUNNING_MAN);
        mRunningManThread.start();
        mRunningMainHandler = new Handler(mRunningManThread.getLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // onResume()的時候，我們讓RunningManRunnable開始不停的畫
        resumeRunningMan();
    }

    private void resumeRunningMan() {
        if (mRunningManRunnable == null) {
            mRunningManRunnable = new RunningManRunnable();
            mRunningMainHandler.post(mRunningManRunnable);

        } else {
            mRunningManRunnable.setPlaying(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // onPause()的時候，我們讓RunningManRunnable停止畫
        pauseRunningMan();
    }

    private void pauseRunningMan() {
        mRunningManRunnable.setPlaying(false);
    }

    @Override
    protected void onDestroy() {
        releaseRunningManThread();

        super.onDestroy();
    }

    private void releaseRunningManThread() {
        mRunningMainHandler.removeCallbacks(mRunningManRunnable);
        mRunningManThread.quit();
        mRunningManRunnable = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
