package com.dl.dlexerciseandroid.ui.instagramapi.feedview.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.instagramapi.IGImage;
import com.squareup.picasso.Picasso;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class IGImageViewHolder extends BaseViewHolder {

    private ImageView mIgImage;


    public IGImageViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        mIgImage = (ImageView) itemView.findViewById(R.id.image_view_instagram_api_ig_image);
    }

    @Override
    public void bind(IGImage igImage) {
        Picasso.with(itemView.getContext())
                .load(igImage.getStandardUrl())
                .placeholder(R.drawable.ic_login_avatar).into(mIgImage);
    }
}
