package com.dl.dlexerciseandroid.backgroundtask.task.loadimage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utils.BitmapUtils;
import com.dl.dlexerciseandroid.utils.HttpUtils;

import java.lang.ref.WeakReference;

/**
 * Created by logicmelody on 2016/7/15.
 */

// 使用AsyncTask load image搭配RecyclerView顯示images，我們每次在onBindViewHolder()的時候，都會啟動一個新的AsyncTask來load圖，
// 但RecyclerView的特性是，每一個item會重複利用，這樣有可能會發生一個情況：
//
// 1. A item已經在load一張a image，但是還沒有load完成
// 2. 使用者快速滑動RecyclerView的list，導致A item又被重複利用，這時候在load b image
// 3. b image先load完成，之後a image才load完成
// => 這會導致最後出來的結果是顯示a image，而不是最新的b image
//
// 所以我們實作了這個ImageLoader用來解決Multithreading的問題
public class ImageLoader {

    private Resources mResources;
    private LruCache<String, Bitmap> mMemoryCache;

    private int mReqWidth = 0;
    private int mReqHeight = 0;

    private boolean mUsePlaceHolder = false;

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

        // Void...代表我們在new一個ImageAsyncTask出來的時候，可以傳入多個變數
        // ex: new ImageAsyncTask().execute(para1, para2, para3, ......);
        //
        // 但是這邊我們使用的情況是一次只傳入一個image的resource
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap;

            // Background load image完成之後，我們要將<key, value> = <Image Uri, Bitmap>塞到LruCache中
            if (TextUtils.isEmpty(mUri)) {
                bitmap = loadBitmapFromResources();
                addBitmapToMemoryCache(String.valueOf(mResId), bitmap);

            } else {
                bitmap = loadBitmapFromUri();
                addBitmapToMemoryCache(mUri, bitmap);
            }

            return bitmap;
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
            ImageLoaderAsyncTask imageLoaderAsyncTask = getImageLoaderAsyncTask(imageView);

            if (imageView != null && this == imageLoaderAsyncTask) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    // 我們用一個Drawable，並且把他assign給ImageView
    // Drawable的class裡面會包一個load image的AsyncTask，用這個Drawable的class來與ImageView產生連結
    //
    // 這邊其實用一個Object物件包一個AsyncTask也可以，只要用setTag()的方式將Object set給ImageView即可
    private class DownloadedDrawable extends BitmapDrawable {

        private WeakReference<ImageLoaderAsyncTask> mImageLoaderAsyncTaskReference;


        public DownloadedDrawable(Bitmap bitmap, ImageLoaderAsyncTask task) {
            super(mResources, bitmap);
            mImageLoaderAsyncTaskReference = new WeakReference<>(task);
        }

        public ImageLoaderAsyncTask getImageLoaderAsyncTask() {
            return mImageLoaderAsyncTaskReference.get();
        }
    }

    public ImageLoader(Resources resources, boolean usePlaceHolder) {
        mResources = resources;
        mUsePlaceHolder = usePlaceHolder;
    }

    public void load(String url, ImageView imageView) {
        // Load一張image之前，會先拿這張image的uri去LruCache中檢查是不是已經有load好的bitmap可以直接使用
        // 有：就可以直接拿來使用
        // 沒有：才會啟動一個AsyncTask去load image
        Bitmap bitmapFromCache = getBitmapFromMemCache(url);
        if (bitmapFromCache != null) {
            imageView.setImageBitmap(bitmapFromCache);
            return;
        }

        if (!cancelPotentialLoad(url, imageView)) {
            return;
        }

        ImageLoaderAsyncTask task = new ImageLoaderAsyncTask(url, imageView);
        DownloadedDrawable downloadedDrawable =
                new DownloadedDrawable(mUsePlaceHolder ?
                        BitmapFactory.decodeResource(mResources, R.drawable.image_placeholder) : null, task);

        imageView.setImageDrawable(downloadedDrawable);
        task.execute();
    }

    public void load(int resId, ImageView imageView) {
        // Load一張image之前，會先拿這張image的uri去LruCache中檢查是不是已經有load好的bitmap可以直接使用
        // 有：就可以直接拿來使用
        // 沒有：才會啟動一個AsyncTask去load image
        Bitmap bitmapFromCache = getBitmapFromMemCache(String.valueOf(resId));
        if (bitmapFromCache != null) {
            imageView.setImageBitmap(bitmapFromCache);
            return;
        }

        if (!cancelPotentialLoad(resId, imageView)) {
            return;
        }

        ImageLoaderAsyncTask task = new ImageLoaderAsyncTask(resId, imageView);
        DownloadedDrawable downloadedDrawable =
                new DownloadedDrawable(mUsePlaceHolder ?
                        BitmapFactory.decodeResource(mResources, R.drawable.image_placeholder) : null, task);

        imageView.setImageDrawable(downloadedDrawable);
        task.execute();
    }

    // 判斷目前的AsyncTask需不需要cancel
    private boolean cancelPotentialLoad(String uri, ImageView imageView) {
        ImageLoaderAsyncTask imageLoaderAsyncTask = getImageLoaderAsyncTask(imageView);

        if (imageLoaderAsyncTask != null) {
            String imageUri = imageLoaderAsyncTask.getImageUri();

            if (TextUtils.isEmpty(imageUri) || !imageUri.equals(uri)) {
                imageLoaderAsyncTask.cancel(true);

            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }

        return true;
    }

    private boolean cancelPotentialLoad(int resId, ImageView imageView) {
        ImageLoaderAsyncTask imageLoaderAsyncTask = getImageLoaderAsyncTask(imageView);

        if (imageLoaderAsyncTask != null) {
            int imageResId = imageLoaderAsyncTask.getResourceId();

            if (imageResId != resId) {
                imageLoaderAsyncTask.cancel(true);

            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }

        return true;
    }

    // 取得與ImageView產生連結的Drawable -> 取得Drawable中的AsyncTask
    private ImageLoaderAsyncTask getImageLoaderAsyncTask(ImageView imageView) {
        if (imageView == null) {
            return null;
        }

        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof DownloadedDrawable) {
            DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
            return downloadedDrawable.getImageLoaderAsyncTask();
        }

        return null;
    }

    public void setReqWidthHeight(int reqWidth, int reqHeight) {
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
    }

    public void setMemoryCache(LruCache<String, Bitmap> memoryCache) {
        mMemoryCache = memoryCache;
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache == null) {
            return;
        }

        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        if (mMemoryCache == null) {
            return null;
        }

        return mMemoryCache.get(key);
    }
}
