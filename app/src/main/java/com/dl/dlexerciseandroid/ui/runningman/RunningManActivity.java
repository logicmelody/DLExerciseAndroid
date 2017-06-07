package com.dl.dlexerciseandroid.ui.runningman;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.dl.dlexerciseandroid.R;

public class RunningManActivity extends AppCompatActivity {

    private static final String THREAD_RUNNING_MAN = "com.dl.dlexerciseandroid.THREAD_RUNNING_MAN";

    private SurfaceView mSurfaceView;

    // 用一條HandlerThread跟Handler來處理畫running man的動畫
    private HandlerThread mRunningManThread;
    private Handler mRunningMainHandler;
    private RunningManRunnable mRunningManRunnable;


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
    }

    private void setupRunningManThread() {
        mRunningManThread = new HandlerThread(THREAD_RUNNING_MAN);
        mRunningManThread.start();

        mRunningManRunnable = new RunningManRunnable(getResources(), mSurfaceView);
        mRunningMainHandler = new Handler(mRunningManThread.getLooper());
        mRunningMainHandler.post(mRunningManRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // onResume()的時候，我們讓RunningManRunnable開始不停的畫
        resumeRunningMan();
    }

    private void resumeRunningMan() {
        mRunningManRunnable.setPlaying(true);
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
