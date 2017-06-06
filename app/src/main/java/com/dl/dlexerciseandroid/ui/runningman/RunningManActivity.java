package com.dl.dlexerciseandroid.ui.runningman;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;

public class RunningManActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_man);
        initialize();
    }

    private void initialize() {
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setTitle(getString(R.string.all_running_man));
        actionBar.setDisplayHomeAsUpEnabled(true);
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
