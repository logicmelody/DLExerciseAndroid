package com.dl.dlexerciseandroid.utility.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.utility.component.PackedString;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by logicmelody on 2016/4/27.
 */
public class DoItLaterUtils {

    public static final String ACTION_DO_IT_LATER = "com.dl.dlexerciseandroid.ACTION_DO_IT_LATER";

    public static final class ExtraKey {
        public static final String TITLE = "extra_do_it_later_title";
        public static final String DESCRIPTION = "extra_do_it_later_description";
        public static final String PACKAGE_NAME = "extra_do_it_later_package_name";
        public static final String CALL_BACK = "extra_do_it_later_call_back";
    }

    // 用來將從db取出的packed string重組成一個intent
    // 要重組以下資訊：
    // Action, PackageName, ClassName, Data(Uri), Flag, Type, Extra datas
    public static Intent composeDoItLaterIntentFrom(Context context, String laterCallback) {
        PackedString ps = new PackedString(laterCallback, context);
        Map<String, Object> map = ps.unpack();
        Intent intent = new Intent();

        String packageName = null;
        String className = null;

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();

            if (PackedString.Key.ACTION.equals(key)) {
                String action = (String) map.get(key);

                if (!TextUtils.isEmpty(action)) {
                    intent.setAction(action);
                }

            } else if (PackedString.Key.PACKAGE_NAME.equals(key)) {
                packageName = (String) map.get(key);

            } else if (PackedString.Key.CLASS_NAME.equals(key)) {
                className = (String) map.get(key);

            } else if (PackedString.Key.DATA.equals(key)) {
                String uri = (String) map.get(key);

                if (!TextUtils.isEmpty(uri)) {
                    intent.setData(Uri.parse(uri));
                }

            } else if (PackedString.Key.FLAG.equals(key)) {
                String flag = (String) map.get(key);

                if (!TextUtils.isEmpty(flag)) {
                    intent.setFlags(Integer.valueOf(flag));
                }

            } else if (PackedString.Key.TYPE.equals(key)) {
                String type = (String) map.get(key);

                if (!TextUtils.isEmpty(type)) {
                    intent.setType(type);
                }

            } else {
                // 擷取intent組成packed string的時候，一定要setPackageName(packageName)
                // 不然這個地方會出錯
                intent.putExtra(key, (Serializable) map.get(key));
            }
        }

        if (!TextUtils.isEmpty(packageName)) {
            if (!TextUtils.isEmpty(className)) {
                intent.setClassName(packageName, className);

            } else {
                intent.setPackage(packageName);
            }
        }

        return intent;
    }
}
