package com.dl.dlexerciseandroid.ui.bubbletext;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/7/3.
 */

public class BubbleTextService extends Service {

    private WindowManager mWindowManager;

    private View mBubbleTextView;


    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        setupBubbleTextView();
        findViews();
    }

    private void setupBubbleTextView() {
        WindowManager.LayoutParams params = null;

        if (mBubbleTextView != null) {
            params = (WindowManager.LayoutParams) mBubbleTextView.getLayoutParams();
            mWindowManager.removeView(mBubbleTextView);
        }

        // 產生BubbleTextView
        mBubbleTextView = LayoutInflater.from(this).inflate(R.layout.layout_bubble_text_view, null);

        if (params == null) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, // TYPE_SYSTEM_ALERT才可以正常運作
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        }

        // 使用WindowManager把view加到window上
        mWindowManager.addView(mBubbleTextView, params);
    }

    private void findViews() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
