package com.dl.dlexerciseandroid.ui.bottomnavigationview;

/**
 * Created by logicmelody on 2017/7/20.
 */

public class BottomNavigationPresenter implements BottomNavigationContract.Presenter {

    private BottomNavigationContract.View mBottomNavigationView;


    public BottomNavigationPresenter(BottomNavigationContract.View view) {
        mBottomNavigationView = view;
    }

    @Override
    public void changeContent(String content) {
        mBottomNavigationView.setContent(content);
    }
}
