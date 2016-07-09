package com.dl.dlexerciseandroid.utility.component;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by logicmelody on 2016/6/27.
 */
public class LoadImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

    private ImageView mTargetImageView;

    private String mUri;
    private int mResId;

    private int mReqWidth = -1;
    private int mReqHeight = -1;


    public LoadImageAsyncTask(int resId, ImageView targetImageView) {
        mResId = resId;
        mTargetImageView = targetImageView;
    }

    public LoadImageAsyncTask(int resId, ImageView targetImageView, int reqWidth, int reqHeight) {
        mResId = resId;
        mTargetImageView = targetImageView;
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mTargetImageView.setImageBitmap(bitmap);
    }
}
