package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListFragment;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class LruFragment extends ImageListFragment {

    public static final String TAG = LruFragment.class.getName();

    private LruCache<String, Bitmap> mMemoryCache;


    @Override
    protected ImageListAdapter getImageListAdapter(Context context, List<String> imageDataList) {
        setupMemoryCache();
        return new LruAdapter(context, imageDataList, mMemoryCache);
    }

    private void setupMemoryCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Runtime.getRuntime().maxMemory()可以拿到目前這個app可以使用的最大量memory
        Log.d("danny", "LruFragment ===> Max runtime memory = " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + " Mb");

        // Use 1/10th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 10;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.

                // 將bitmap的byte數轉換成我們定義的單位(這邊是用kilobytes)
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    protected String getImageUri() {
        //return "http://i.imgur.com/DvpvklR.png";
        return "http://herogamesworld.com/images/iron%20man%20games.jpg";
    }
}
