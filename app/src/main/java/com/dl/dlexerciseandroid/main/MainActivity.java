package com.dl.dlexerciseandroid.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dl.dlexerciseandroid.R;

public class MainActivity extends AppCompatActivity {

    private UIController mUIController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUIController = new UIController(this);
        mUIController.onCreate(savedInstanceState);
    }
}
