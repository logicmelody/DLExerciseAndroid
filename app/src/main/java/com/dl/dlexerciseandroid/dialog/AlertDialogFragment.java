package com.dl.dlexerciseandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * Created by logicmelody on 2016/4/13.
 */

// 在Android我們呼叫一個Dialog的方式，都要借助DialogFragment來呼叫我們需要使用的Dialog
public class AlertDialogFragment extends DialogFragment {

    public static final String TAG = "com.dl.dlexerciseandroid.AlertDialogFragment";

    public static final String EXTRA_MESSAGE = "AlertDialogFragment_extra_message";

    // Dialog跟呼叫他的Activity or Fragment要透過interface的方式做溝通
    public interface OnClickAlertDialogListener {
        void onClickAlertDialogOk();
        void onClickAlertDialogCancel();
    }

    private OnClickAlertDialogListener mOnClickAlertDialogListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 判斷呼叫此Dialog的object是Activity or Fragment
        if (activity instanceof OnClickAlertDialogListener) {
            mOnClickAlertDialogListener = (OnClickAlertDialogListener) activity;

        } else if (getTargetFragment() instanceof OnClickAlertDialogListener) {
            mOnClickAlertDialogListener = (OnClickAlertDialogListener) getTargetFragment();
        }
    }

    // 回傳Dialog，不管是自己實作的，或是Android內建的widget
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // AlertDialog除非content部分已經有別的widget或是custom的layout，才會使用setTitle()
        // 不然用setMessage()來顯示title即可
        builder.setMessage(getArguments().getString(EXTRA_MESSAGE, ""))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mOnClickAlertDialogListener == null) {
                            return;
                        }

                        // 將AlertDialog的OK and Cancel button透過interface的方式，
                        // 將實作的部分給呼叫此Dialog的object處理(這邊是MainActivity)
                        mOnClickAlertDialogListener.onClickAlertDialogOk();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mOnClickAlertDialogListener == null) {
                            return;
                        }

                        // 將AlertDialog的OK and Cancel button透過interface的方式，
                        // 將實作的部分給呼叫此Dialog的object處理(這邊是MainActivity)
                        mOnClickAlertDialogListener.onClickAlertDialogCancel();
                    }
                });

        return builder.create();
    }
}
