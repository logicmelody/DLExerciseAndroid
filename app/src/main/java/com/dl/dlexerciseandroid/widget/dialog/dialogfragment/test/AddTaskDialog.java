package com.dl.dlexerciseandroid.widget.dialog.dialogfragment.test;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class AddTaskDialog extends Dialog {

    private Context mContext;


    public AddTaskDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_task);
        initialize();
    }

    private void initialize() {

    }
}
