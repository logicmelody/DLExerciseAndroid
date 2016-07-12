package com.dl.dlexerciseandroid.loadimagefrominternet.picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class PicassoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_view_load_image_from_internet_image);
        }
    }

    private Context mContext;

    private List<String> mImageUriList;


    public PicassoAdapter(Context context, List<String> imageUriList) {
        mContext = context;
        mImageUriList = imageUriList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_load_image_from_internet_image_list, parent, false));
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

    @Override
    public int getItemCount() {
        return mImageUriList.size();
    }
}
