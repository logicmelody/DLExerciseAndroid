package com.dl.dlexerciseandroid.bitmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.task.loadimage.ImageLoader;

/**
 * Created by logicmelody on 2016/6/27.
 */
public class BitmapFragment extends Fragment {

    public static final String TAG = BitmapFragment.class.getName();

    private Context mContext;

    private ImageView mOriginalImageCenterScale;
    private ImageView mResizedImage;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bitmap, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupOriginalImage();
        setupResizedImage();
    }

    private void findViews() {
        mOriginalImageCenterScale = (ImageView) getView().findViewById(R.id.image_view_bitmap_original_image_center_scale);
        mResizedImage = (ImageView) getView().findViewById(R.id.image_view_bitmap_resized_image);
    }

    private void setupOriginalImage() {
        new ImageLoader(mContext.getResources(), true).load(R.drawable.poster_iron_man, mOriginalImageCenterScale);

        // 用Picasso可以達到一樣的效果
        //Picasso.with(mContext).load(R.drawable.poster_iron_man).into(mOriginalImageCenterScale);
    }

    private void setupResizedImage() {
        ImageLoader imageLoader = new ImageLoader(mContext.getResources(), true);
        imageLoader.setReqWidthHeight(400, 400);
        imageLoader.load(R.drawable.poster_iron_man, mResizedImage);

//        mResizedImage.setImageBitmap(BitmapUtils.
//                decodeSampledBitmapFromResource(getResources(), R.drawable.poster_iron_man, 200, 200));
    }
}
