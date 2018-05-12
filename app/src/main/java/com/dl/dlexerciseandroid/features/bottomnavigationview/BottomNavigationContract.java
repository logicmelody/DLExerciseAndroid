package com.dl.dlexerciseandroid.features.bottomnavigationview;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

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
