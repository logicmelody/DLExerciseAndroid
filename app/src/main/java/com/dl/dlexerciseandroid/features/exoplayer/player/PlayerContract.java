package com.dl.dlexerciseandroid.features.exoplayer.player;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

/**
 * Created by logicmelody on 2018/3/18.
 */

public interface PlayerContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<PlayerContract.Presenter> {

    }
}
