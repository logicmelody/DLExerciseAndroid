package com.dl.dlexerciseandroid.features.bubbletext;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/7/3.
 */

public class BubbleTextService extends Service {

    public static final class Action {
        public static final String ACTION_GET_NEW_MESSAGE = "com.dl.dlexerciseandroid.ui.bubbletext.ACTION_GET_NEW_MESSAGE";
    }

    public static final class Extra {
        public static final String EXTRA_MESSAGE = "com.dl.dlexerciseandroid.ui.bubbletext.EXTRA_MESSAGE";
    }

    private WindowManager mWindowManager;

    private View mBubbleTextView;
    private TextView mmBubbleTextViewMessage;
    private ImageView mCloseBubbleTextViewButton;


    public static Intent generateUpdateBubbleTextViewMessageIntent(Context context, String message) {
        Intent intent = new Intent(context, BubbleTextService.class);
        intent.setAction(BubbleTextService.Action.ACTION_GET_NEW_MESSAGE);
        intent.putExtra(BubbleTextService.Extra.EXTRA_MESSAGE, message);

        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        setupBubbleTextView();
        findViews();
        setupViews();
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
        mmBubbleTextViewMessage = (TextView) mBubbleTextView.findViewById(R.id.text_view_bubble_text_view_message);
        mCloseBubbleTextViewButton = (ImageView) mBubbleTextView.findViewById(R.id.image_view_bubble_text_view_close);
    }

    private void setupViews() {
        mCloseBubbleTextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BubbleTextService.this.stopSelf();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (Action.ACTION_GET_NEW_MESSAGE.equals(action)) {
            updateBubbleTextViewMessage(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateBubbleTextViewMessage(Intent intent) {
        if (intent == null) {
            return;
        }

        String message = intent.getStringExtra(Extra.EXTRA_MESSAGE);

        mmBubbleTextViewMessage.setText(TextUtils.isEmpty(message) ? "" : message);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mBubbleTextView != null) {
            mWindowManager.removeView(mBubbleTextView);
        }

        super.onDestroy();
    }
}
