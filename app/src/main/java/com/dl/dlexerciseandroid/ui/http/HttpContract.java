package com.dl.dlexerciseandroid.ui.http;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * Created by logicmelody on 2018/3/13.
 */

public interface HttpContract {

    interface View extends BaseView<HttpContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
