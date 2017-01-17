package com.dl.dlexerciseandroid.ui.bottomnavigationview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dl.dlexerciseandroid.R;

public class BottomNavigationViewActivity extends AppCompatActivity {

    public static final String TAG = BottomNavigationViewActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        initialize();
    }

    private void initialize() {
        findViews();
    }

    private void findViews() {

    }
}
