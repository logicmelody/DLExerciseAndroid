package com.dl.dlexerciseandroid.datastructure.instagramapi;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class IGImage {

    private String mId;
    private String mThumbnailUrl;
    private String mStandardUrl;

    private long mCreatedTime;
    private int mLikeCount;


    public IGImage(String id, String thumbnailUrl, String standardUrl, long createdTime, int likeCount) {
        mId = id;
        mThumbnailUrl = thumbnailUrl;
        mStandardUrl = standardUrl;
        mCreatedTime = createdTime;
        mLikeCount = likeCount;
    }

    public String getId() {
        return mId;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getStandardUrl() {
        return mStandardUrl;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IGImage").append("\n")
                     .append("Id = ").append(mId).append("\n")
                     .append("ThumbnailUrl = ").append(mThumbnailUrl).append("\n")
                     .append("StandardUrl = ").append(mStandardUrl).append("\n")
                     .append("CreatedTime = ").append(mCreatedTime).append("\n")
                     .append("LikeCount = ").append(mLikeCount).append("\n");

        return stringBuilder.toString();
    }
}
