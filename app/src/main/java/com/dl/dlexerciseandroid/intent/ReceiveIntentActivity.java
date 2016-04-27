package com.dl.dlexerciseandroid.intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dl.dlexerciseandroid.R;

public class ReceiveIntentActivity extends AppCompatActivity {

    public static final String ACTION_RECEIVE_INTENT = "com.dl.dlexerciseandroid.ACTION_RECEIVE_INTENT";

    private Toolbar mToolbar;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_intent);

        Log.d("danny", "ReceiveIntentActivity get intent action = " + getIntent().getAction());

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
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
