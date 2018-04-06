package com.dl.dlexerciseandroid.ui.rxjava;

import android.graphics.drawable.Drawable;

import com.dl.dlexerciseandroid.ui.BasePresenter;
import com.dl.dlexerciseandroid.ui.BaseView;

/**
 * Created by logicmelody on 2018/4/1.
 */

public interface RxJavaContract {

    interface Presenter extends BasePresenter {

        void testFromArray();

        void testPrintHelloWorld();

        void testEmpty();

        void testFlatMap();

        void loadIronMan();

        void onDestroy();
    }

    interface View extends BaseView<RxJavaContract.Presenter> {

        void showLog(String tag, String log);

        void setLogText();

        Drawable getIronManDrawable();

        void setIronManImageView(Drawable drawable);

        void showToast(String text);
    }
}
