package com.dl.dlexerciseandroid.ui.dialog;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

public interface DialogTriggerContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<DialogTriggerContract.Presenter> {

    }
}
