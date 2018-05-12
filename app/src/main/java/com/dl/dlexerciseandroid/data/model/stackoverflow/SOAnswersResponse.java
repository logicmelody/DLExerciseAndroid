package com.dl.dlexerciseandroid.data.model.stackoverflow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class SOAnswersResponse {

    // This annotation is needed for Gson to map the JSON keys with our fields.
    @SerializedName("items")
    // This annotation indicates that this member should be exposed for JSON serialization or deserialization.
    @Expose
    private List<SOItem> mSOItems = null;

    @SerializedName("has_more")
    @Expose
    private boolean mHasMore;

    @SerializedName("backoff")
    @Expose
    private int mBackoff;

    @SerializedName("quota_max")
    @Expose
    private int mQuotaMax;

    @SerializedName("quota_remaining")
    @Expose
    private int mQuotaRemaining;


    public List<SOItem> getItems() {
        return mSOItems;
    }

    public void setItems(List<SOItem> items) {
        this.mSOItems = items;
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.mHasMore = hasMore;
    }

    public int getBackoff() {
        return mBackoff;
    }

    public void setBackoff(int backoff) {
        this.mBackoff = backoff;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }

    public void setQuotaMax(int quotaMax) {
        this.mQuotaMax = quotaMax;
    }

    public int getQuotaRemaining() {
        return mQuotaRemaining;
    }

    public void setQuotaRemaining(int quotaRemaining) {
        this.mQuotaRemaining = quotaRemaining;
    }
}
