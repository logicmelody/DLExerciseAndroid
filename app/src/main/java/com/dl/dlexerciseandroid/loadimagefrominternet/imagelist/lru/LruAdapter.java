package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.task.loadimage.ImageLoader;
import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListAdapter;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class LruAdapter extends ImageListAdapter {

    private LruCache<String, Bitmap> mMemoryCache;


    public LruAdapter(Context context, List<String> imageUriList, LruCache<String, Bitmap> memoryCache) {
        super(context, imageUriList);
        mMemoryCache = memoryCache;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder vh = (ImageViewHolder) holder;

        ImageLoader imageLoader = new ImageLoader(mContext.getResources(), true);
        imageLoader.setMemoryCache(mMemoryCache);
        imageLoader.load(mImageUriList.get(position), vh.image);
    }
}
