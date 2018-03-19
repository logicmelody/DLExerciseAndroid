package com.dl.dlexerciseandroid.ui.exoplayer.main;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

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
