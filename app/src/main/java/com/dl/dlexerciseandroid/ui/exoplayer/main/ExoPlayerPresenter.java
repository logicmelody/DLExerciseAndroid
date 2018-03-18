package com.dl.dlexerciseandroid.ui.exoplayer.main;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class ExoPlayerPresenter implements ExoPlayerContract.Presenter {

    private ExoPlayerContract.View mView;


    public ExoPlayerPresenter(ExoPlayerContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
