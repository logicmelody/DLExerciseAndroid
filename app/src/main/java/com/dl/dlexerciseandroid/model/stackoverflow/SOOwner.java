package com.dl.dlexerciseandroid.model.stackoverflow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class SOOwner {

    @SerializedName("reputation")
    @Expose
    private int mReputation;

    @SerializedName("user_id")
    @Expose
    private int mUserId;

    @SerializedName("user_type")
    @Expose
    private String mUserType;

    @SerializedName("accept_rate")
    @Expose
    private int mAcceptRate;

    @SerializedName("profile_image")
    @Expose
    private String mProfileImage;

    @SerializedName("display_name")
    @Expose
    private String mDisplayName;

    @SerializedName("link")
    @Expose
    private String mLink;


    public int getReputation() {
        return mReputation;
    }

    public void setReputation(Integer reputation) {
        this.mReputation = reputation;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        this.mUserId = userId;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        this.mUserType = userType;
    }

    public int getAcceptRate() {
        return mAcceptRate;
    }

    public void setAcceptRate(Integer acceptRate) {
        this.mAcceptRate = acceptRate;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        this.mProfileImage = profileImage;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }
}
