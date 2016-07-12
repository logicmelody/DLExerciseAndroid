package com.dl.dlexerciseandroid.loadimagefrominternet.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.loadimagefrominternet.picasso.PicassoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
abstract public class ImageListFragment extends Fragment {

    protected abstract void setupImageList();

    protected Context mContext;

    protected RecyclerView mImageList;

    protected List<String> mImageDataList = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setImageData();
        setupImageList();
    }

    private void findViews() {
        mImageList = (RecyclerView) getView().findViewById(R.id.recycler_view_image_list);
    }

    private void setImageData() {
        for (int i = 0 ; i < 100 ; i++) {
            mImageDataList.add("http://i.imgur.com/DvpvklR.png");
        }
    }
}
