package com.dl.dlexerciseandroid.features.exoplayer.player;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class PlayerPresenter implements PlayerContract.Presenter {

    private PlayerContract.View mView;


    public PlayerPresenter(PlayerContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
