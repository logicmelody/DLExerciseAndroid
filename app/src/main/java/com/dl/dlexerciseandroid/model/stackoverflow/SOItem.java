package com.dl.dlexerciseandroid.model.stackoverflow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class SOItem {

    @SerializedName("owner")
    @Expose
    private SOOwner mSOOwner;

    @SerializedName("is_accepted")
    @Expose
    private boolean mIsAccepted;

    @SerializedName("score")
    @Expose
    private int mScore;

    @SerializedName("last_activity_date")
    @Expose
    private int mLastActivityDate;

    @SerializedName("creation_date")
    @Expose
    private int mCreationDate;

    @SerializedName("answer_id")
    @Expose
    private int mAnswerId;

    @SerializedName("question_id")
    @Expose
    private int mQuestionId;

    @SerializedName("last_edit_date")
    @Expose
    private int mLastEditDate;

    private int mViewType;


    public SOOwner getSOOwner() {
        return mSOOwner;
    }

    public void setSOOwner(SOOwner owner) {
        this.mSOOwner = owner;
    }

    public boolean isIsAccepted() {
        return mIsAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.mIsAccepted = isAccepted;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        this.mScore = score;
    }

    public int getLastActivityDate() {
        return mLastActivityDate;
    }

    public void setLastActivityDate(int lastActivityDate) {
        this.mLastActivityDate = lastActivityDate;
    }

    public int getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(int creationDate) {
        this.mCreationDate = creationDate;
    }

    public int getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(int answerId) {
        this.mAnswerId = answerId;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        this.mQuestionId = questionId;
    }

    public int getLastEditDate() {
        return mLastEditDate;
    }

    public void setLastEditDate(int lastEditDate) {
        this.mLastEditDate = lastEditDate;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }
}
