package com.dl.dlexerciseandroid.dialog.dialogfragment.fullscreen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

// DialogFragment有兩種呈現View的方式：
// 1. onCreateView(): 這種方式有一個好處，就是可以是情況把這個DialogFragment當成一個Dialog跳出來，或是當成一個一般的Fragment放在UI中，
//    但是用這種方法有一個缺點，就是沒有onBackPressed()的callback，所以沒有辦法偵測user什麼時候按下back key
// 2. onCreateDialog(): 這種View的create方式就只能把這個DialogFragment當dialog使用，但是用此方法，
//                      可以在Dialog中偵測到onBackPressed()的事件
public class FullscreenDialogFragment extends DialogFragment {

    public static final String TAG = FullscreenDialogFragment.class.getName();

    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FullscreenDialog fullscreenDialog = new FullscreenDialog(mContext);

        return fullscreenDialog;
    }
}
