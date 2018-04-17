package com.dl.dlexerciseandroid.dialog.dialogfragment.fullscreen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;

import com.dl.dlexerciseandroid.R;

import butterknife.ButterKnife;

// 如果要將這個Dialog變成fullscreen顯示，要做以下兩件事：
// 1. Constructor中要在super中設定android.R.style.Theme
// 2. 在onCreate()中要設定requestWindowFeature(Window.FEATURE_NO_TITLE)
public class FullscreenDialog extends Dialog {

    private Context mContext;


    public FullscreenDialog(@NonNull Context context) {
        super(context, android.R.style.Theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_fullscreen_dialog);
        ButterKnife.bind(this, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("danny", "FullscreenDialog onBackPressed()");
    }
}
