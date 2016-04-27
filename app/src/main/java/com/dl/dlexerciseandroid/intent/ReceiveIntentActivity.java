package com.dl.dlexerciseandroid.intent;

import android.content.Intent;
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

import java.util.Iterator;

public class ReceiveIntentActivity extends AppCompatActivity {

    public static final String ACTION_RECEIVE_INTENT = "com.dl.dlexerciseandroid.ACTION_RECEIVE_INTENT";

    private Toolbar mToolbar;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_intent);
        retrieveIntentInfo();
        initialize();
    }

    private void retrieveIntentInfo() {
        if (getIntent() == null) {
            return;
        }

        Intent intent = getIntent();

        // 可以取得所有intent中的extra data
        Bundle bundle = intent.getExtras();

        // 以下資訊可能不會有
        String action = intent.getAction();
        String type = intent.getType();
        String data = intent.getData() != null ? intent.getData().toString() : null;
        String flag = String.valueOf(intent.getFlags());

        // 一定會有此資訊
        String packageName = intent.getComponent().getPackageName();

        // 一定會有此資訊
        String className = intent.getComponent().getClassName();

        Log.d("danny", "Receive intent action = " + action);
        Log.d("danny", "Receive intent packageName = " + packageName);
        Log.d("danny", "Receive intent className = " + className);
        Log.d("danny", "Receive intent type = " + type);
        Log.d("danny", "Receive intent data = " + data);
        Log.d("danny", "Receive intent flag = " + flag);

        // Extra data
        if (bundle != null) {
            Iterator<String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Log.d("danny", "Extra [" + key + "] = " + bundle.get(key));
            }
        }
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
