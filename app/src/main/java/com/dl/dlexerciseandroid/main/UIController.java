package com.dl.dlexerciseandroid.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dl.dlexerciseandroid.R;


/**
 * Created by logicmelody on 2016/3/28.
 */
public class UIController {

    private AppCompatActivity mActivity;

    private Toolbar mToolBar;
    private ActionBar mActionBar;


    public UIController(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
    }

    private void findViews() {
        mToolBar = (Toolbar) mActivity.findViewById(R.id.tool_bar);
    }

    private void setupActionBar() {
        mActivity.setSupportActionBar(mToolBar);
        mActionBar = mActivity.getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setTitle(mActivity.getString(R.string.all_overview));
            mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }
}
