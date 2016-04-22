package com.dl.dlexerciseandroid.doitlater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DoItLaterShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("danny", "DoItLaterShareActivity Get shared intent");

        // 開啟存task的service之後，我們就把這個用來接收shared intent的Activity關掉
        finish();
    }
}
