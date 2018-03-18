package com.dl.dlexerciseandroid.ui.exoplayer.player;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * Created by logicmelody on 2018/3/18.
 */

public interface PlayerContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<PlayerContract.Presenter> {

    }
}
