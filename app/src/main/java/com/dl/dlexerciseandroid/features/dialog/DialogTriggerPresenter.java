package com.dl.dlexerciseandroid.features.dialog;

public class DialogTriggerPresenter implements DialogTriggerContract.Presenter {

    private DialogTriggerContract.View mView;


    public DialogTriggerPresenter(DialogTriggerContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void openFullscreenDialog() {
        mView.showFullscreenDialog();
    }

    @Override
    public void openDatePickerDialog() {
        mView.showDatePickerDialog();
    }
}
