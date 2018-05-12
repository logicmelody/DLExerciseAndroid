package com.dl.dlexerciseandroid.features.exoplayer.main;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

/**
 * Created by logicmelody on 2018/3/18.
 */

public interface ExoPlayerMainContract {

    interface View extends BaseView<ExoPlayerMainContract.Presenter> {

    	void openPlayerActivity();
    }

    interface Presenter extends BasePresenter {

    	void playTestVideo();
    }
}
