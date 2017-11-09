package com.dl.dlexerciseandroid.ui.instagramapi.feedview.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGMedia;
import com.dl.dlexerciseandroid.view.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class IGImageViewHolder extends BaseViewHolder {

    private DynamicHeightNetworkImageView mIgImage;
    private TextView mLikeCountTextView;


    public IGImageViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
        setupViews();
    }

    @Override
    protected void findViews(View itemView) {
        mIgImage = (DynamicHeightNetworkImageView) itemView.findViewById(R.id.image_view_instagram_api_ig_image);
        mLikeCountTextView = (TextView) itemView.findViewById(R.id.text_view_instagram_api_feed_item_like_count);
    }

    private void setupViews() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void bind(IGMedia igMedia) {
        bindImage(igMedia);
        bindLikeCount(igMedia);
    }

    private void bindImage(IGMedia igMedia) {
        Picasso.with(itemView.getContext())
                .load(igMedia.getImages().getStandardResolution().getUrl())
                .into(mIgImage);

        // 因為我們是用StaggeredGridLayoutManager的方式顯示，這樣會造成ImageView的width是確定的，但是height不確定
        // 所以在動態load image的時候會造成因為height不確定，顯示的時候會出現問題
        // 解法：我們customize一個ImageView(DynamicHeightNetworkImageView)，把image的ratio(width/height)設定好，
        //      如此一來ImageView的width和height就會變成固定，顯示上就沒有問題了
        mIgImage.setAspectRatio(igMedia.getRatio());
    }

    private void bindLikeCount(IGMedia igMedia) {
        StringBuilder sb = new StringBuilder();
        int likeCount = igMedia.getLikes().getCount() < 0 ? 0 : igMedia.getLikes().getCount();

        sb.append(likeCount).append(" ").append(itemView.getContext().getString(R.string.instagram_api_likes));

        mLikeCountTextView.setText(sb.toString());
    }
}
