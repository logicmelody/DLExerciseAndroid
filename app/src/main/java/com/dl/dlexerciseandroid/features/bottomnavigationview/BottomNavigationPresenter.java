package com.dl.dlexerciseandroid.features.bottomnavigationview;

/**
 * Created by logicmelody on 2017/7/20.
 */

public class BottomNavigationPresenter implements BottomNavigationContract.Presenter {

    private BottomNavigationContract.View mBottomNavigationView;


    public BottomNavigationPresenter(BottomNavigationContract.View view) {
        mBottomNavigationView = view;
        mBottomNavigationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void changeContent(String content) {
        mBottomNavigationView.setContent(content);
    }

    @Override
    public void finishActivity() {
        mBottomNavigationView.finish();
    }
}
