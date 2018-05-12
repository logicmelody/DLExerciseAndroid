package com.dl.dlexerciseandroid.features.http;

/**
 * Created by logicmelody on 2018/3/13.
 */

public class HttpPresenter implements HttpContract.Presenter {

    private HttpContract.View mView;


    public HttpPresenter(HttpContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
