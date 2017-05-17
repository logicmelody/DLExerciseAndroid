package com.dl.dlexerciseandroid.ui.doitlater.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dl.dlexerciseandroid.backgroundtask.service.TaskService;
import com.dl.dlexerciseandroid.utility.utils.DoItLaterUtils;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DoItLaterReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("danny", "DoItLaterReceiverActivity Get shared intent");

        String action = getIntent().getAction();

        if (action.equals(DoItLaterUtils.ACTION_DO_IT_LATER) || action.equals(Intent.ACTION_SEND)) {
            // 開啟存task的service之後，我們就把這個用來接收shared intent的Activity關掉
            startService(TaskService.generateSaveDoItLaterTaskIntent(this, getIntent()));
        }

        finish();
    }
}
