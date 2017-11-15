package com.dl.dlexerciseandroid.ui.bottomnavigationview;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 *
 * 在這個contract中，先將View跟Presenter之間的互動關係給定義好
 */
public interface BottomNavigationContract {

    interface View extends BaseView<Presenter> {
        boolean selectNavigationItem(int menuItemId);
        void setContent(String content);
        void finish();
    }

    interface Presenter extends BasePresenter {
        void changeContent(String content);
        void finishActivity();
    }
}
