package com.dl.dlexerciseandroid.background.task.loadimage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.BitmapUtils;
import com.dl.dlexerciseandroid.utility.utils.HttpUtils;

import java.lang.ref.WeakReference;

/**
 * Created by logicmelody on 2016/7/15.
 */
public class ImageLoader {

    private Resources mResources;

    private int mReqWidth = 0;
    private int mReqHeight = 0;

    // AsyncTask一定要extends之後才可以使用
    // 有了這個AsyncTask，可以讓load image的時候，UI thread不會被block住
    private class ImageLoaderAsyncTask extends AsyncTask<Void, Void, Bitmap> {

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

        private String mUri;
        private int mResId;


        public String getImageUri() {
            return mUri;
        }

        public int getResourceId() {
            return mResId;
        }

        // Load image from resources
        public ImageLoaderAsyncTask(int resId, ImageView targetImageView) {
            mResId = resId;
            mTargetImageViewReference = new WeakReference<>(targetImageView);
        }

        // Load image from URI
        public ImageLoaderAsyncTask(String uri, ImageView targetImageView) {
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

        private Bitmap loadBitmapFromUri() {
            return HttpUtils.downloadBitmap(mUri);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled() || mTargetImageViewReference == null || bitmap == null) {
                return;
            }

            ImageView imageView = mTargetImageViewReference.get();

            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

//    private class DownloadedDrawable extends BitmapDrawable {
//
//        private WeakReference<ImageDownloaderAsyncTask> mImageDownloaderAsyncTaskReference;
//
//
//        public DownloadedDrawable(Resources res, Bitmap bitmap, ImageDownloaderAsyncTask task) {
//            super(res, bitmap);
//            mImageDownloaderAsyncTaskReference = new WeakReference<>(task);
//        }
//
//        public ImageDownloaderAsyncTask getImageDownloaderAsyncTask() {
//            return mImageDownloaderAsyncTaskReference.get();
//        }
//    }

    public ImageLoader(Resources resources) {
        mResources = resources;
    }

    public void load(String url, ImageView imageView) {
        ImageLoaderAsyncTask task = new ImageLoaderAsyncTask(url, imageView);
        task.execute();
    }

    public void load(int resId, ImageView imageView) {
        ImageLoaderAsyncTask task = new ImageLoaderAsyncTask(resId, imageView);
        task.execute();
    }

    public void setReqWidthHeight(int reqWidth, int reqHeight) {
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
    }

//    private boolean cancelPotentialDownload(String url, ImageView imageView) {
//        ImageDownloaderAsyncTask task = getImageDownloaderAsyncTask(imageView);
//
//        if (task != null) {
//            String imageUri = task.getImageUri();
//
//            if (TextUtils.isEmpty(imageUri) || !imageUri.equals(url)) {
//                task.cancel(true);
//
//            } else {
//                // The same URL is already being downloaded.
//                return false;
//            }
//        }
//
//        return true;
//    }
}
