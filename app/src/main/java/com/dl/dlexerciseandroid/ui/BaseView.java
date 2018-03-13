package com.dl.dlexerciseandroid.ui;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
