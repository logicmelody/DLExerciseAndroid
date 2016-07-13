package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.task.ImageAsyncTask;
import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.squareup.picasso.Picasso;

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

        ImageAsyncTask imageAsyncTask = new ImageAsyncTask(mContext.getResources(), mImageUriList.get(position), vh.image);
        imageAsyncTask.setPlaceHolder(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.image_placeholder));
        imageAsyncTask.execute();
    }
}
