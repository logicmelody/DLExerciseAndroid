package com.dl.dlexerciseandroid.features.countdown;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

/**
 * Created by logicmelody on 2018/1/6.
 */

public class CountDownContract {

    public interface Presenter extends BasePresenter {
        void startCountDown(int sec);
    }

    public interface View extends BaseView<Presenter> {
        void showCountDownDialog(int sec);
    }
}
