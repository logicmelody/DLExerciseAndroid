package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListFragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class LruFragment extends ImageListFragment {

    public static final String TAG = LruFragment.class.getName();

    public static final String EXTRA_MEMORY_CACHE = "com.dl.dlexerciseandroid.EXTRA_MEMORY_CACHE";

    private LruCache<String, Bitmap> mMemoryCache;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setupMemoryCache(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    private void setupMemoryCache(Bundle savedInstanceState) {
        createMemoryCache();
        restoreMemoryCache(savedInstanceState);
    }

    private void createMemoryCache() {
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

    private void restoreMemoryCache(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        HashMap<String, Bitmap> map = (HashMap<String, Bitmap>) savedInstanceState.getSerializable(EXTRA_MEMORY_CACHE);

        if (map == null || map.keySet() == null) {
            return;
        }

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Bitmap bitmap = map.get(key);

            mMemoryCache.put(key, bitmap);
        }
    }

    @Override
    protected ImageListAdapter getImageListAdapter(Context context, List<String> imageDataList) {
        return new LruAdapter(context, imageDataList, mMemoryCache);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveMemoryCacheState(outState);
    }

    // In onSaveInstanceState, try using LruCache.snapshot() to get a Map of your cache entries,
    // ordered from least to most recently used. You could then write each key,value into the Bundle.
    // Then when restoring from the Bundle, iterate each entry and put() them back into the LruCache.
    private void saveMemoryCacheState(Bundle outState) {
        // 因為HashMap是Serializable的object，才可以塞到bundle中傳出去
        HashMap<String, Bitmap> map = (HashMap<String, Bitmap>) mMemoryCache.snapshot();
        outState.putSerializable(EXTRA_MEMORY_CACHE, map);
    }

    @Override
    protected String getImageUri() {
        //return "http://i.imgur.com/DvpvklR.png";
        return "http://herogamesworld.com/images/iron%20man%20games.jpg";
    }
}
