package com.dl.dlexerciseandroid.background.task;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.BitmapUtils;

import java.lang.ref.WeakReference;

/**
 * Created by logicmelody on 2016/7/9.
 */

// AsyncTask一定要extends之後才可以使用
// 有了這個AsyncTask，可以讓load image的時候，UI thread不會被block住
public class ImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

    // Use a WeakReference to ensure the ImageView can be garbage collected.
    //
    // The WeakReference to the ImageView ensures that the AsyncTask does not prevent the ImageView
    // and anything it references from being garbage collected.
    // There’s no guarantee the ImageView is still around when the task finishes,
    // so you must also check the reference in onPostExecute().
    // The ImageView may no longer exist,
    // if for example, the user navigates away from the activity or if a configuration change happens before
    // the task finishes.
    private final WeakReference<ImageView> mTargetImageViewReference;

    private Resources mResources;

    private String mUri;
    private int mResId;

    private int mReqWidth = 0;
    private int mReqHeight = 0;


    // Load image from resources
    public ImageAsyncTask(Resources resources, int resId, ImageView targetImageView) {
        mResources = resources;
        mResId = resId;
        mTargetImageViewReference = new WeakReference<>(targetImageView);
    }

    // Load image from URI
    public ImageAsyncTask(Resources resources, String uri, ImageView targetImageView) {
        mResources = resources;
        mUri = uri;
        mTargetImageViewReference = new WeakReference<>(targetImageView);
    }

    public void setPlaceHolder(Bitmap placeHolder) {
        if (mTargetImageViewReference == null || placeHolder == null) {
            return;
        }

        ImageView imageView = mTargetImageViewReference.get();

        if (imageView != null) {
            imageView.setImageBitmap(placeHolder);
        }
    }

    public void setReqWidthHeight(int reqWidth, int reqHeight) {
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
    }

    // Void...代表我們在new一個ImageAsyncTask出來的時候，可以傳入多個變數
    // ex: new ImageAsyncTask().execute(para1, para2, para3, ......);
    //
    // 但是這邊我們使用的情況是一次只傳入一個image的resource
    @Override
    protected Bitmap doInBackground(Void... params) {
        if (TextUtils.isEmpty(mUri)) {
            return loadBitmapFromResources();

        } else {
            return loadBitmapFromUri();
        }
    }

    private Bitmap loadBitmapFromResources() {
        if (mReqWidth != 0 && mReqHeight != 0) {
            return BitmapUtils.decodeSampledBitmapFromResource(mResources, R.drawable.poster_iron_man, mReqWidth, mReqHeight);

        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();

            // 將這個option設定為true的時候，在decode一張image的時候，不會真的allocate memory，但是可以得到這張image的width, height
            // 或一些其他的資訊
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(mResources, mResId, options);

            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            String imageType = options.outMimeType;

            Log.d("danny", "Original image width = " + imageWidth);
            Log.d("danny", "Original image height = " + imageHeight);
            Log.d("danny", "Original image type = " + imageType);

            return BitmapFactory.decodeResource(mResources, mResId);
        }
    }

    // TODO: loadBitmapFromUri()
    private Bitmap loadBitmapFromUri() {
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (mTargetImageViewReference == null || bitmap == null) {
            return;
        }

        ImageView imageView = mTargetImageViewReference.get();

        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
