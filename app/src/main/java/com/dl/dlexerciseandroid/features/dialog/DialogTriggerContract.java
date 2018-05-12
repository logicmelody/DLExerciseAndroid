package com.dl.dlexerciseandroid.features.dialog;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

public interface DialogTriggerContract {

    interface Presenter extends BasePresenter {

        void openFullscreenDialog();

        void openDatePickerDialog();
    }

    interface View extends BaseView<DialogTriggerContract.Presenter> {

        void showFullscreenDialog();

        void showDatePickerDialog();
    }
}
