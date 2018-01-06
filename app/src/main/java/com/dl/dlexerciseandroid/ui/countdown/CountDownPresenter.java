package com.dl.dlexerciseandroid.ui.countdown;

/**
 * Created by logicmelody on 2018/1/6.
 */

public class CountDownPresenter implements CountDownContract.Presenter {

    private CountDownContract.View mView;


    public CountDownPresenter(CountDownContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
