package com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.lru;

import android.content.Context;

import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListAdapter;
import com.dl.dlexerciseandroid.loadimagefrominternet.imagelist.base.ImageListFragment;

import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class LruFragment extends ImageListFragment {

    public static final String TAG = LruFragment.class.getName();


    @Override
    protected ImageListAdapter getImageListAdapter(Context context, List<String> imageDataList) {
        return new LruAdapter(context, imageDataList);
    }

    @Override
    protected String getImageUri() {
        return "http://i.imgur.com/DvpvklR.png";
        //return "http://herogamesworld.com/images/iron%20man%20games.jpg";
    }
}
