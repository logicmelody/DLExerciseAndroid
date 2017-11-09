package com.dl.dlexerciseandroid.model.instagramapi.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/9.
 */

// 我們可以只定義我們需要用到的fields，如果這個class中有inner class，跟定義一般class的做法一樣
public class IGMedia {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("images")
    @Expose
    private Images mImages;

    @SerializedName("likes")
    @Expose
    private Likes mLikes;

    @SerializedName("created_time")
    @Expose
    private long mCreatedTime;

    // 如果不想要跟Gson有相關的field，在前面加上transient即可
    private transient float mRatio = 1F;


    public class Images {

        @SerializedName("thumbnail")
        @Expose
        private Thumbnail mThumbnail;

        @SerializedName("standard_resolution")
        @Expose
        private StandardResolution mStandardResolution;


        public Thumbnail getThumbnail() {
            return mThumbnail;
        }

        public void setThumbnail(Thumbnail thumbnail) {
            this.mThumbnail = thumbnail;
        }

        public StandardResolution getStandardResolution() {
            return mStandardResolution;
        }

        public void setStandardResolution(StandardResolution standardResolution) {
            this.mStandardResolution = standardResolution;
        }

        @Override
        public String toString() {
            return "Images{" +
                    "mThumbnail=" + mThumbnail.toString() +
                    ", mStandardResolution=" + mStandardResolution.toString() +
                    '}';
        }
    }

    public class Likes {

        @SerializedName("count")
        @Expose
        private int mCount;


        public int getCount() {
            return mCount;
        }

        public void setCount(int count) {
            this.mCount = count;
        }

        @Override
        public String toString() {
            return "Likes{" +
                    "mCount=" + mCount +
                    '}';
        }
    }

    public class Thumbnail {

        @SerializedName("url")
        @Expose
        private String mUrl;

        @SerializedName("width")
        @Expose
        private int mWidth;

        @SerializedName("height")
        @Expose
        private int mHeight;

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            this.mUrl = url;
        }

        public int getWidth() {
            return mWidth;
        }

        public void setWidth(int width) {
            this.mWidth = width;
        }

        public int getHeight() {
            return mHeight;
        }

        public void setHeight(int height) {
            this.mHeight = height;
        }

        @Override
        public String toString() {
            return "Thumbnail{" +
                    "mUrl='" + mUrl + '\'' +
                    ", mWidth=" + mWidth +
                    ", mHeight=" + mHeight +
                    '}';
        }
    }

    public class StandardResolution {

        @SerializedName("url")
        @Expose
        private String mUrl;

        @SerializedName("width")
        @Expose
        private int mWidth;

        @SerializedName("height")
        @Expose
        private int mHeight;

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            this.mUrl = url;
        }

        public int getWidth() {
            return mWidth;
        }

        public void setWidth(int width) {
            this.mWidth = width;
        }

        public int getHeight() {
            return mHeight;
        }

        public void setHeight(int height) {
            this.mHeight = height;
        }

        @Override
        public String toString() {
            return "StandardResolution{" +
                    "mUrl='" + mUrl + '\'' +
                    ", mWidth=" + mWidth +
                    ", mHeight=" + mHeight +
                    '}';
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public Images getImages() {
        return mImages;
    }

    public void setImages(Images images) {
        this.mImages = images;
    }

    public Likes getLikes() {
        return mLikes;
    }

    public void setLikes(Likes likes) {
        this.mLikes = likes;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        this.mCreatedTime = createdTime;
    }

    public float getRatio() {
        return mRatio;
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    public float calculateRatio() {
        return (float) mImages.getStandardResolution().getWidth() /
               (float) mImages.getStandardResolution().getHeight();
    }

    @Override
    public String toString() {
        return "IGMedia{" +
                "mId='" + mId + '\'' +
                ", mImages=" + mImages.toString() +
                ", mLikes=" + mLikes.toString() +
                ", mCreatedTime=" + mCreatedTime +
                ", mRatio=" + mRatio +
                '}';
    }
}
