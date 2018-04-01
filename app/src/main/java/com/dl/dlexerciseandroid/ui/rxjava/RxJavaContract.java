package com.dl.dlexerciseandroid.ui.rxjava;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * Created by logicmelody on 2018/4/1.
 */

public interface RxJavaContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<RxJavaContract.Presenter> {

    }
}
