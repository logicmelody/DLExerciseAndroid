package com.dl.dlexerciseandroid.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.facebook.FacebookFragment;
import com.dl.dlexerciseandroid.overview.OverviewFragment;
import com.dl.dlexerciseandroid.rightdrawer.RightDrawerFragment;
import com.dl.dlexerciseandroid.spring.ConsumingRestfulWebServiceFragment;
import com.dl.dlexerciseandroid.test.TestFragment;
import com.dl.dlexerciseandroid.utility.FbUtils;
import com.dl.dlexerciseandroid.utility.FragmentUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


/**
 * Created by logicmelody on 2016/3/28.
 */
public class UIController {

    private AppCompatActivity mActivity;
    private FragmentManager mFragmentManager;

    private Fragment mCurrentFragment;

    private Toolbar mToolBar;
    private ActionBar mActionBar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private CallbackManager mCallbackManager;
    private LoginButton mFbLoginButton;


    public UIController(AppCompatActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void onCreate(Bundle savedInstanceState) {
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupMainContent();
        setupDrawerLayout();
        setupLeftDrawer();
        setupRightDrawer();
        setupFacebook();
    }

    private void findViews() {
        mToolBar = (Toolbar) mActivity.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout_main);
        mNavigationView = (NavigationView) mActivity.findViewById(R.id.navigation_view_main_left_side_drawer);

        // 如果我們想要get HeaderView裡面的其他View，比如：Button, TextView，我們可以用以下方式來取得HeaderView
        // There is usually only 1 header view.
        // Multiple header views can technically be added at runtime.
        // We can use navigationView.getHeaderCount() to determine the total number.
        View headerLayout = mNavigationView.getHeaderView(0);
        mFbLoginButton = (LoginButton) headerLayout.findViewById(R.id.fb_login_button_left_drawer_header);
    }

    private void setupActionBar() {
        mActivity.setSupportActionBar(mToolBar);
        mActionBar = mActivity.getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(mActivity.getString(R.string.all_overview));
            //mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }

    private void setupMainContent() {
        addFragmentTo(OverviewFragment.class, R.id.frame_layout_main_container, FragmentUtils.FragmentTag.OVERVIEW);
    }

    private void setupDrawerLayout() {
        // ActionBarDrawerToggle這個class是將原本action bar(Toolbar)上的up button變成hamburger的關鍵，
        // 同時他也可以當成drawer layout的callback listener使用
        // 他有兩種constructor：
        //
        // public ActionBarDrawerToggle (Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
        //                               int openDrawerContentDescRes, int closeDrawerContentDescRes)
        //
        // public ActionBarDrawerToggle (Activity activity, DrawerLayout drawerLayout,
        //                               int openDrawerContentDescRes, int closeDrawerContentDescRes)
        //
        // 差別在於有沒有傳Toolbar的object，如果這個Activity有用Toolbar取代掉原本的Action bar，記得要用有傳Toolbar的他有兩種constructor
        mActionBarDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolBar,
                                                           R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void setupLeftDrawer() {
        // NavigationView要設callback listener，處理click item之後要做什麼事情，這邊是replace對應的Fragment
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_left_drawer_overview:
                        replaceFragmentTo(OverviewFragment.class, R.id.frame_layout_main_container, FragmentUtils.FragmentTag.OVERVIEW);
                        break;

                    case R.id.menu_item_left_drawer_consuming_restful_web_service:
                        replaceFragmentTo(ConsumingRestfulWebServiceFragment.class, R.id.frame_layout_main_container,
                                FragmentUtils.FragmentTag.CONSUMING_RESTFUL_WEB_SERVICE);
                        break;

                    case R.id.menu_item_left_drawer_facebook:
                        replaceFragmentTo(FacebookFragment.class, R.id.frame_layout_main_container,
                                FragmentUtils.FragmentTag.FACEBOOK);
                        break;

                    case R.id.menu_item_left_drawer_test:
                        replaceFragmentTo(TestFragment.class, R.id.frame_layout_main_container, FragmentUtils.FragmentTag.TEST);
                        break;
                }

                item.setChecked(true);
                mActionBar.setTitle(item.getTitle());
                closeLeftDrawer();

                return true;
            }
        });
    }

    private void setupRightDrawer() {
        addFragmentTo(RightDrawerFragment.class, R.id.frame_layout_main_right_side_drawer, FragmentUtils.FragmentTag.RIGHT_DRAWER);
    }

    private void addFragmentTo(Class<? extends Fragment> fragmentClass, int containerId, String fragmentTag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                transaction.add(containerId, fragment, fragmentTag);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        transaction.commit();
    }

    private void replaceFragmentTo(Class<? extends Fragment> fragmentClass, int containerId, String fragmentTag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragmentTransaction.replace(containerId, fragment, fragmentTag);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mCurrentFragment = fragment;

        fragmentTransaction.commit();
    }

    private void setupFacebook() {
        FacebookSdk.sdkInitialize(mActivity);
        mCallbackManager = CallbackManager.Factory.create();

        // Facebook SDK會在token要過期之前將token更新
        // 頻率為一天一次，在user有用app向FB server發出request的時候更新token
        // 但是如果超過60天沒有更新token，token就會過期，這時候就要重新再走一次login flow
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null || token.isExpired()) {
            Log.d("danny", "token is null");

            setupFbLoginButton();
            setFbLoginButtonVisibility(true);

        } else {
            Log.d("danny", token.getToken());
            Log.d("danny", "Approved permissions: " + token.getPermissions().toString());
            Log.d("danny", "Declined permissions: " + token.getDeclinedPermissions().toString());

            // 已經有token的狀況，user啟用app之後，我們更新token
            AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
                @Override
                public void OnTokenRefreshed(AccessToken accessToken) {
                    Log.d("danny", "OnTokenRefreshed");
                }

                @Override
                public void OnTokenRefreshFailed(FacebookException exception) {
                    Log.d("danny", "OnTokenRefreshFailed");
                }
            });
            setFbLoginButtonVisibility(false);
        }
    }

    private void setupFbLoginButton() {
        // 如果LoginButton是在Fragment中，需要call以下這行
        // mFbLoginButton.setFragment(this);

        // Login時的permission設定，只要求app中必須用到的permission
        mFbLoginButton.setReadPermissions(Arrays.asList(FbUtils.Permission.PUBLIC_PROFILE));
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Login成功，拿到token
                Log.d("danny", "FB login onSuccess");

                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                Log.d("danny", AccessToken.getCurrentAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // user可能在permission頁面點選"Cancel"
                Log.d("danny", "FB login onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("danny", "FB login onError");
                Log.e("danny", error.toString());
            }
        });
    }

    private void setFbLoginButtonVisibility(boolean isVisible) {

    }

    private void closeLeftDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onResume() {
        // Facebook
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(mActivity);
    }

    public void onPause() {
        // Facebook
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(mActivity);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        // 要記得在Activity的onPostCreate()中call mActionBarDrawerToggle.syncState()，不然hamburger會沒有變化
        // 使用DrawerToggle一定要完成此步驟

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // 使用DrawerToggle一定要完成此步驟

        // Pass any configuration change to the drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // 使用DrawerToggle一定要完成此步驟
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        } else {
            switch (item.getItemId()) {
                default:
                    return false;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果有跳出別的視窗或是Activity，一定要實作CallbackManager及這個method，才可以運作正常
        // (保險起見，只要有用到Facebook API的地方，最後都要實作這步驟)
        // 必須要把onActivityResult()的結果傳給CallbackManager
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
