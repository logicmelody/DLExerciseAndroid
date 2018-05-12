package com.dl.dlexerciseandroid.features.http;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

/**
 * Created by logicmelody on 2018/3/13.
 */

public interface HttpContract {

    interface View extends BaseView<HttpContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
