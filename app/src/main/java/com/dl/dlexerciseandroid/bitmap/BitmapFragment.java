package com.dl.dlexerciseandroid.bitmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.BitmapUtils;

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
        BitmapFactory.Options options = new BitmapFactory.Options();

        // 將這個option設定為true的時候，在decode一張image的時候，不會真的allocate memory，但是可以得到這張image的width, height
        // 或一些其他的資訊
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.poster_iron_man, options);

        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        String imageType = options.outMimeType;

        Log.d("danny", "Original image width = " + imageWidth);
        Log.d("danny", "Original image height = " + imageHeight);
        Log.d("danny", "Original image type = " + imageType);

        mOriginalImageCenterScale.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.poster_iron_man));
    }

    private void setupResizedImage() {
        mResizedImage.setImageBitmap(BitmapUtils.
                decodeSampledBitmapFromResource(getResources(), R.drawable.poster_iron_man, 200, 200));
    }
}
