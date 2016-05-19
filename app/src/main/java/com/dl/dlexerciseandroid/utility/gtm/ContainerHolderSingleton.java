package com.dl.dlexerciseandroid.utility.gtm;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by logicmelody on 2016/5/19.
 */

// 在整個app中只會存在一個ContainerHolder來負責處理有關GTM的container，所以我們必須有一個singleton的class
public class ContainerHolderSingleton {

    private static ContainerHolder sContainerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return sContainerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        sContainerHolder = c;
    }
}
