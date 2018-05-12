package com.dl.dlexerciseandroid.widget.dialog.dialogfragment.countdown;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2018/1/6.
 */
public class CountDownDialogFragment extends DialogFragment {

    public static final String TAG = CountDownDialogFragment.class.getName();

    public static final String EXTRA_INT_COUNT_DOWN_SECONDS = "com.dl.dlexerciseandroid.EXTRA_INT_COUNT_DOWN_SECONDS";

    private static final int DEFAULT_COUNT_DOWN_SECONDS = 3;

    private int mCountDownSeconds = 0;


    public class CountDownDialog extends Dialog {

        private Context mContext;

        @BindView(R.id.text_view_count_down_text)
        public TextView mTextViewCountDown;


        public CountDownDialog(Context context) {
            // 第二個參數可以設定dialog的style
            super(context, R.style.FullScreenDialog);
            mContext = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_count_down);
            ButterKnife.bind(this, this);
            startCountDown();
        }

        private void startCountDown() {
            mTextViewCountDown.setText(String.valueOf(mCountDownSeconds));

            new CountDownTimer(mCountDownSeconds * 1000L, 1000L) {

                public void onTick(long millisUntilFinished) {
                    mTextViewCountDown.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    dismiss();
                }

            }.start();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCountDownSeconds = getArguments().getInt(EXTRA_INT_COUNT_DOWN_SECONDS, DEFAULT_COUNT_DOWN_SECONDS);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CountDownDialog countDownDialog = new CountDownDialog(getContext());
        countDownDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        return countDownDialog;
    }
}
