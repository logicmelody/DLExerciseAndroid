package com.dl.dlexerciseandroid.features.countdown;

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

    @Override
    public void startCountDown(int sec) {
        mView.showCountDownDialog(sec);
    }
}
