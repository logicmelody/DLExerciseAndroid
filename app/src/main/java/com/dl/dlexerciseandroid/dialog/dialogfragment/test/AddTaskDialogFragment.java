package com.dl.dlexerciseandroid.dialog.dialogfragment.test;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class AddTaskDialogFragment extends DialogFragment {

    public static final String TAG = AddTaskDialogFragment.class.getName();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AddTaskDialog(getContext());
    }
}
