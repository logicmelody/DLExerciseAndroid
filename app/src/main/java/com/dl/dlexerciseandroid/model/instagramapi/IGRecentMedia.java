package com.dl.dlexerciseandroid.model.instagramapi;

import java.util.List;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class IGRecentMedia {

    private String mNextMaxId;
    private List<IGImage> mImageList;


    public IGRecentMedia(String nextMaxId, List<IGImage> imageList) {
        mNextMaxId = nextMaxId;
        mImageList = imageList;
    }

    public String getNextMaxId() {
        return mNextMaxId;
    }

    public List<IGImage> getImageList() {
        return mImageList;
    }
}
