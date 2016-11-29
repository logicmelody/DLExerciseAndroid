package com.dl.dlexerciseandroid.ui.loadimagefrominternet.imagelist.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_view_load_image_from_internet_image);
        }
    }

    protected Context mContext;

    protected List<String> mImageUriList;


    public ImageListAdapter(Context context) {
        mContext = context;
        mImageUriList = new ArrayList<>();
    }

    public ImageListAdapter(Context context, List<String> imageUriList) {
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

    }

    @Override
    public int getItemCount() {
        return mImageUriList.size();
    }

    public void add(String uri) {
        mImageUriList.add(uri);
    }
}
