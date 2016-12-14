package com.dl.dlexerciseandroid.ui.main;

import android.app.Application;
import android.view.animation.AnimationUtils;

import com.dl.dlexerciseandroid.utility.utils.GeneralUtils;
import com.facebook.FacebookSdk;

/**
 * Created by logicmelody on 2016/4/6.
 */

// 這個Application class通常都在進行一些初始化的動作，有很多SDK或是Library都需要在這邊初始化
// 創了這個class之後，記得在AndroidManifest中也要設定，讓系統去讀這個class
// ex: Parse, Facebook
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        initAnimation();
        initFacebookSdk();
    }

    private void initAnimation() {
        GeneralUtils.sFadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        GeneralUtils.sFadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
    }

    private void initFacebookSdk() {
        // 初始化Facebook SDK元件
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
