package com.dl.dlexerciseandroid.doitlater.share;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dl.dlexerciseandroid.utility.component.TaskPackedString;
import com.dl.dlexerciseandroid.utility.utils.DoItLaterUtils;

import java.util.Iterator;

/**
 * Created by logicmelody on 2016/4/27.
 */
public class InHouseDoItLaterTask extends DoItLaterTask {

    public InHouseDoItLaterTask(Intent intent) {
        super(intent);
    }

    @Override
    public void retrieveIntent(Intent intent) {
        Intent callBackIntent = intent.getParcelableExtra(DoItLaterUtils.ExtraKey.CALL_BACK);

        mTitle = intent.getStringExtra(DoItLaterUtils.ExtraKey.TITLE);
        mDescription = intent.getStringExtra(DoItLaterUtils.ExtraKey.DESCRIPTION);
        mTime = System.currentTimeMillis();

        retrieveCallbackIntent(callBackIntent);
    }

    private void retrieveCallbackIntent(Intent callBackIntent) {
        TaskPackedString.Builder psBuilder = new TaskPackedString.Builder();

        // 可以取得所有intent中的extra data
        Bundle bundle = callBackIntent.getExtras();

        // 以下資訊可能不會有
        String action = callBackIntent.getAction();
        String type = callBackIntent.getType();
        String data = callBackIntent.getData() != null ? callBackIntent.getData().toString() : null;
        String flag = String.valueOf(callBackIntent.getFlags());

        // 一定會有以下兩個資訊
        String packageName = callBackIntent.getComponent().getPackageName();
        String className = callBackIntent.getComponent().getClassName();

        Log.d("danny", "Receive callBackIntent action = " + action);
        Log.d("danny", "Receive callBackIntent packageName = " + packageName);
        Log.d("danny", "Receive callBackIntent className = " + className);
        Log.d("danny", "Receive callBackIntent type = " + type);
        Log.d("danny", "Receive callBackIntent data = " + data);
        Log.d("danny", "Receive callBackIntent flag = " + flag);

        if (!TextUtils.isEmpty(action)) {
            psBuilder.put(TaskPackedString.Key.ACTION, action);
        }

        if (!TextUtils.isEmpty(type)) {
            psBuilder.put(TaskPackedString.Key.TYPE, type);
        }

        if (!TextUtils.isEmpty(data)) {
            psBuilder.put(TaskPackedString.Key.DATA, data);
        }

        if (!TextUtils.isEmpty(flag)) {
            psBuilder.put(TaskPackedString.Key.FLAG, flag);
        }

        if (!TextUtils.isEmpty(packageName)) {
            psBuilder.put(TaskPackedString.Key.PACKAGE_NAME, packageName);
        }

        if (!TextUtils.isEmpty(className)) {
            psBuilder.put(TaskPackedString.Key.CLASS_NAME, className);
        }

        // Extra data
        if (bundle != null) {
            Iterator<String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                psBuilder.put(key, bundle.get(key));
                Log.d("danny", "Extra [" + key + "] = " + bundle.get(key));
            }
        }

        mLaterPackageName = packageName;
        mLaterCallback = psBuilder.toString();
    }
}
