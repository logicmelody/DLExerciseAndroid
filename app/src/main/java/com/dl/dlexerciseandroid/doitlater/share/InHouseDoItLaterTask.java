package com.dl.dlexerciseandroid.doitlater.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dl.dlexerciseandroid.utility.component.PackedString;
import com.dl.dlexerciseandroid.utility.utils.DoItLaterUtils;

import java.util.Iterator;

/**
 * Created by logicmelody on 2016/4/27.
 */
public class InHouseDoItLaterTask extends DoItLaterTask {

    public InHouseDoItLaterTask(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public void retrieveIntent(Context context, Intent intent) {
        Intent callBackIntent = intent.getParcelableExtra(DoItLaterUtils.ExtraKey.CALL_BACK);

        mTitle = intent.getStringExtra(DoItLaterUtils.ExtraKey.TITLE);
        mDescription = intent.getStringExtra(DoItLaterUtils.ExtraKey.DESCRIPTION);
        mTime = System.currentTimeMillis();

        retrieveCallbackIntent(context, callBackIntent);
    }

    private void retrieveCallbackIntent(Context context, Intent callBackIntent) {
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

        PackedString.Builder psBuilder = new PackedString.Builder();

        // 超級重要：為了可以傳遞別的app中自己定義的class，PackedString.Builder一定要設定package name
        psBuilder.setPackageName(packageName);

        if (!TextUtils.isEmpty(action)) {
            psBuilder.put(PackedString.Key.ACTION, action);
        }

        if (!TextUtils.isEmpty(type)) {
            psBuilder.put(PackedString.Key.TYPE, type);
        }

        if (!TextUtils.isEmpty(data)) {
            psBuilder.put(PackedString.Key.DATA, data);
        }

        if (!TextUtils.isEmpty(flag)) {
            psBuilder.put(PackedString.Key.FLAG, flag);
        }

        if (!TextUtils.isEmpty(packageName)) {
            psBuilder.put(PackedString.Key.PACKAGE_NAME, packageName);
        }

        if (!TextUtils.isEmpty(className)) {
            psBuilder.put(PackedString.Key.CLASS_NAME, className);
        }

        // 可以取得所有intent中的extra data
        Bundle bundle = callBackIntent.getExtras();
        if (bundle != null) {
            try {
                // 取得別的app環境底下的class loader，如此一來才可以認得別的app中自己定義的class
                // 還要搭配兩個東西，才可以使這個功能正常運作：
                // 1. 在AndroidManifest中要加入android:sharedUserId="android.uid.latertask"，如此一來有加入這個property的
                //    app彼此的resources或是class才可以共用
                // 2. 彼此app都要sign相同的key
                Context remote = context.createPackageContext(packageName,
                        Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

                // 因為我們要擷取別的app傳來的intent資訊，所以必須要assign那個app環境底下的class loader
                bundle.setClassLoader(remote.getClassLoader());

                // Extra data
                // Note: 在Do It Later的架構中，extra data的部分所有可以Serializable的object都可以傳遞
                Iterator<String> it = bundle.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    psBuilder.put(key, bundle.get(key));
                    Log.d("danny", "Extra [" + key + "] = " + bundle.get(key));
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        mLaterPackageName = packageName;
        mLaterCallback = psBuilder.toString();
    }
}
