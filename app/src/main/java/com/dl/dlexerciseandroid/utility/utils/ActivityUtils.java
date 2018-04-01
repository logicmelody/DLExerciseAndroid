package com.dl.dlexerciseandroid.utility.utils;

import android.content.Context;
import android.content.Intent;

import com.dl.dlexerciseandroid.ui.main.MainActivity;

/**
 * Created by logicmelody on 2018/4/1.
 */

public class ActivityUtils {

    public static void goToMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }
}
