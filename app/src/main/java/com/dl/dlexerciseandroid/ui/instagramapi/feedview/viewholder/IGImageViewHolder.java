package com.dl.dlexerciseandroid.ui.instagramapi.feedview.viewholder;

import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.instagramapi.IGImage;
import com.dl.dlexerciseandroid.utility.component.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class IGImageViewHolder extends BaseViewHolder {

    private DynamicHeightNetworkImageView mIgImage;


    public IGImageViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        mIgImage = (DynamicHeightNetworkImageView) itemView.findViewById(R.id.image_view_instagram_api_ig_image);
    }

    @Override
    public void bind(IGImage igImage) {
        Picasso.with(itemView.getContext())
                .load(igImage.getStandardUrl())
                .into(mIgImage);

        mIgImage.setAspectRatio(igImage.getRatio());
    }
}
