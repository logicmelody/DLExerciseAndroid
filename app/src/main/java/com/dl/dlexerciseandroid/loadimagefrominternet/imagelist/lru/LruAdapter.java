package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;
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

    public LruAdapter(Context context, List<String> imageUriList) {
        super(context, imageUriList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder vh = (ImageViewHolder) holder;

        new ImageLoader(mContext.getResources(), true).load(mImageUriList.get(position), vh.image);
    }
}
