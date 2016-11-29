package com.dl.dlexerciseandroid.ui.loadimagefrominternet.imagelist.picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.ui.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class PicassoAdapter extends ImageListAdapter {

    public PicassoAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder vh = (ImageViewHolder) holder;

        // 使用Picasso來load image已經處理掉以下這些問題
        // Many common pitfalls of image loading on Android are handled automatically by Picasso:
        // 1. Handling ImageView recycling and download cancelation in an adapter.
        // 2. Complex image transformations with minimal memory use.
        // 3. Automatic memory and disk caching.
        Picasso.with(mContext)
                .load(mImageUriList.get(position))
                //.load(R.drawable.poster_iron_man)
                .placeholder(R.drawable.image_placeholder).into(vh.image);
    }
}
