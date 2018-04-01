package com.dl.dlexerciseandroid.ui.rxjava;

/**
 * Created by logicmelody on 2018/4/1.
 */

public class RxJavaPresenter implements RxJavaContract.Presenter {

    private RxJavaContract.View mView;


    public RxJavaPresenter(RxJavaContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
