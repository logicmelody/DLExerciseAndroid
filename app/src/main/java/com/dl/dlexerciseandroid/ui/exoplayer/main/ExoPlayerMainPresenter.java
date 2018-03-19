package com.dl.dlexerciseandroid.ui.exoplayer.main;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class ExoPlayerMainPresenter implements ExoPlayerMainContract.Presenter {

    private ExoPlayerMainContract.View mView;


    public ExoPlayerMainPresenter(ExoPlayerMainContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

	@Override
	public void playTestVideo() {
		mView.openPlayerActivity();
	}
}
