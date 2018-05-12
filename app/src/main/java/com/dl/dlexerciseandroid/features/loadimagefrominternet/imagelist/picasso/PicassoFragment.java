package com.dl.dlexerciseandroid.features.loadimagefrominternet.imagelist.picasso;

import android.content.Context;

import com.dl.dlexerciseandroid.features.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.dl.dlexerciseandroid.features.loadimagefrominternet.imagelist.base.ImageListFragment;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class PicassoFragment extends ImageListFragment {

    public static final String TAG = PicassoFragment.class.getName();


    @Override
    protected ImageListAdapter getImageListAdapter(Context context) {
        return new PicassoAdapter(context);
    }

    @Override
    protected String getImageUri() {
        return "http://i.imgur.com/DvpvklR.png";
        //return "http://herogamesworld.com/images/iron%20man%20games.jpg";
    }
}
