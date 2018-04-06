package com.dl.dlexerciseandroid.ui.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utility.OpenCVUtils;

/**
 * Created by logicmelody on 2017/7/1.
 */

public class OpenCVFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = OpenCVFragment.class.getName();

    private Context mContext;

    private Button mRestoreImageButton;
    private Button mProcessImageButton;

    private ImageView mProcessImageView;


    private class ProcessImageAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private ImageView mImageView;


        public ProcessImageAsyncTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            return getProcessImage(params[0]);
        }

        private Bitmap getProcessImage(Bitmap originalImage) {
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int[] pix = new int[w * h];

            originalImage.getPixels(pix, 0, w, 0, 0, w, h);
            int[] resultInt = OpenCVUtils.getCannyImg(pix, w, h);

            Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);

            return resultImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opencv, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupImageView();
    }

    private void findViews() {
        mRestoreImageButton = (Button) getView().findViewById(R.id.button_opencv_restore_image);
        mProcessImageButton = (Button) getView().findViewById(R.id.button_opencv_process_image);
        mProcessImageView = (ImageView) getView().findViewById(R.id.image_view_opencv_process_image);
    }

    private void setupViews() {
        mRestoreImageButton.setOnClickListener(this);
        mProcessImageButton.setOnClickListener(this);
    }

    private void setupImageView() {
        mProcessImageView.setBackgroundColor(mContext.getResources().getColor(R.color.all_black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_opencv_restore_image:
                restoreImage();

                break;

            case R.id.button_opencv_process_image:
                // 執行Ndk OpenCV的image processing，因為會花比較多的時間，所以這邊開一條AsyncTask去處理
                new ProcessImageAsyncTask(mProcessImageView)
                        .execute(((BitmapDrawable) getResources().getDrawable(R.drawable.image_girl)).getBitmap());

                break;
        }
    }

    private void restoreImage() {
        mProcessImageView.setImageResource(R.drawable.image_girl);
    }
}
