package com.dl.dlexerciseandroid.datastructure.installedapps;

import android.graphics.drawable.Drawable;

/**
 * Created by logicmelody on 2017/10/31.
 */

public class InstalledApp {

    private Drawable mIcon;
    private String mName;


    public InstalledApp(Drawable icon, String name) {
        mIcon = icon;
        mName = name;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getName() {
        return mName;
    }
}
