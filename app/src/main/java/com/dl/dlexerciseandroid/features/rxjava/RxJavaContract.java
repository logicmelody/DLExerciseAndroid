package com.dl.dlexerciseandroid.features.rxjava;

import android.graphics.drawable.Drawable;

import com.dl.dlexerciseandroid.features.BasePresenter;
import com.dl.dlexerciseandroid.features.BaseView;

/**
 * Created by logicmelody on 2018/4/1.
 */

public interface RxJavaContract {

    interface Presenter extends BasePresenter {

        void test5SecToast();

        void testFromArray();

        void testPrintHelloWorld();

        void testEmpty();

        void testFlatMap();

        void loadIronMan();

        void testConcat();

        void stop5SecToast();

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
