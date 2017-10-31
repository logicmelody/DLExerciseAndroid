package com.dl.dlexerciseandroid.datastructure.installedapps;

/**
 * Created by logicmelody on 2017/10/31.
 */

public class InstalledApp {

    private String mIconUri;
    private String mName;


    public InstalledApp(String iconUri, String name) {
        mIconUri = iconUri;
        mName = name;
    }

    public String getIconUri() {
        return mIconUri;
    }

    public String getName() {
        return mName;
    }
}
