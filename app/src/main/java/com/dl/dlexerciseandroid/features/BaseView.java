package com.dl.dlexerciseandroid.features;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
