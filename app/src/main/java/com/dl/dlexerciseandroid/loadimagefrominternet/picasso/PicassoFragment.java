package com.dl.dlexerciseandroid.loadimagefrominternet.picasso;

import android.support.v7.widget.LinearLayoutManager;

import com.dl.dlexerciseandroid.loadimagefrominternet.main.ImageListFragment;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class PicassoFragment extends ImageListFragment {

    public static final String TAG = PicassoFragment.class.getName();

    private PicassoAdapter mPicassoAdapter;


    @Override
    protected void setupImageList() {
        mPicassoAdapter = new PicassoAdapter(mContext, mImageDataList);

        mImageList.setLayoutManager(new LinearLayoutManager(mContext));
        mImageList.setAdapter(mPicassoAdapter);
    }
}
