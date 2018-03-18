package com.dl.dlexerciseandroid.ui.exoplayer;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * Created by logicmelody on 2018/3/18.
 */

public interface ExoPlayerContract {

    interface View extends BaseView<ExoPlayerContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
