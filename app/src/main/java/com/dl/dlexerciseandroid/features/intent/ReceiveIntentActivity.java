package com.dl.dlexerciseandroid.features.intent;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;

import java.util.Iterator;
import java.util.List;

public class ReceiveIntentActivity extends AppCompatActivity {

    public static final String ACTION_RECEIVE_INTENT = "com.dl.dlexerciseandroid.ACTION_RECEIVE_INTENT";

    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private Intent mIntent;

    private List<RunningAppProcessInfo> mRunningAppProcessInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_intent);
        initialize();
        retrieveIntentInfo();
    }

    private void initialize() {
        mIntent = getIntent();
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
            mActionBar.setTitle(ReceiveIntentActivity.class.getSimpleName());
        }
    }

    private void retrieveIntentInfo() {
        if (mIntent == null) {
            return;
        }

        String action = mIntent.getAction();

        retrieveIntentFromAction();
    }

    private void retrieveIntentFromAction() {
        Log.d("danny", "=== retrieveIntentFromAction ===");

        // 可以取得所有intent中的extra data
        Bundle bundle = mIntent.getExtras();

        // 以下資訊可能不會有
        String action = mIntent.getAction();
        String type = mIntent.getType();
        String data = mIntent.getData() != null ? mIntent.getData().toString() : null;
        String flag = String.valueOf(mIntent.getFlags());

        // 一定會有此資訊
        String packageName = mIntent.getComponent().getPackageName();

        // 一定會有此資訊
        String className = mIntent.getComponent().getClassName();

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

    private void retrieveIntentFromShare() {
        Log.d("danny", "=== retrieveIntentFromShare ===");

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        mRunningAppProcessInfoList = am.getRunningAppProcesses();

        for (RunningAppProcessInfo info : mRunningAppProcessInfoList) {
            Log.d("danny", info.processName);
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
