package com.dl.dlexerciseandroid.features.strategypattern.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.features.strategypattern.main.ActivityFinishedReceiver;

abstract public class BasedBehaviorActivity extends AppCompatActivity implements
        ActivityFinishedReceiver.OnActivityFinishedListener {

    private Toolbar mToolbar;

    private ActivityFinishedReceiver mActivityFinishedReceiver;


    abstract protected int getLayoutId();
    abstract protected String getActionBarTitle();
    abstract protected String getFinishedToastString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getActionBarTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerActivityFinishedReceiver();
    }

    private void registerActivityFinishedReceiver() {
        IntentFilter intentFilter = new IntentFilter(ActivityFinishedReceiver.ACTION_ACTIVITY_FINISHED);
        mActivityFinishedReceiver = new ActivityFinishedReceiver(this);

        registerReceiver(mActivityFinishedReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterActivityFinishedReceiver();
    }

    private void unregisterActivityFinishedReceiver() {
        unregisterReceiver(mActivityFinishedReceiver);
        mActivityFinishedReceiver = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sendBroadcast(new Intent(ActivityFinishedReceiver.ACTION_ACTIVITY_FINISHED));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityFinished() {
        Toast.makeText(this, getFinishedToastString(), Toast.LENGTH_SHORT).show();
    }
}
