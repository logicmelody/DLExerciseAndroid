package com.dl.dlexerciseandroid.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.ui.animation.AnimationFragment;
import com.dl.dlexerciseandroid.ui.bitmap.BitmapFragment;
import com.dl.dlexerciseandroid.ui.chat.main.ChatFragment;
import com.dl.dlexerciseandroid.ui.customizedview.CustomizedViewFragment;
import com.dl.dlexerciseandroid.database.debug.AndroidDatabaseManager;
import com.dl.dlexerciseandroid.dialog.dialogfragment.alert.AlertDialogFragment;
import com.dl.dlexerciseandroid.ui.dlsdk.DLSdkFragment;
import com.dl.dlexerciseandroid.ui.facebook.FacebookFragment;
import com.dl.dlexerciseandroid.ui.file.FileFragment;
import com.dl.dlexerciseandroid.ui.firebase.FirebaseFragment;
import com.dl.dlexerciseandroid.ui.intent.IntentFragment;
import com.dl.dlexerciseandroid.ui.loadimagefrominternet.main.LoadImageFromInternetFragment;
import com.dl.dlexerciseandroid.ui.musicplayer.main.MusicPlayerFragment;
import com.dl.dlexerciseandroid.ui.overview.OverviewFragment;
import com.dl.dlexerciseandroid.ui.rightdrawer.RightDrawerFragment;
import com.dl.dlexerciseandroid.ui.spring.ConsumingRestfulWebServiceFragment;
import com.dl.dlexerciseandroid.ui.test.TestFragment;
import com.dl.dlexerciseandroid.ui.time.TimeFragment;
import com.dl.dlexerciseandroid.utility.utils.FbUtils;
import com.dl.dlexerciseandroid.ui.doitlater.tasklist.main.DoItLaterFragment;
import com.dl.dlexerciseandroid.utility.utils.FragmentUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import de.hdodenhof.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


/**
 * Created by logicmelody on 2016/3/28.
 */
public class UIController implements View.OnClickListener {

    public static final class SavedStateKey {
        public static final String FRAGMENT_CLASS_NAME = "com.dl.dlexerciseandroid.SavedStateKey.FRAGMENT_CLASS_NAME";
        public static final String TITLE = "com.dl.dlexerciseandroid.SavedStateKey.TITLE";
    }

    private AppCompatActivity mActivity;
    private FragmentManager mFragmentManager;

    //private Fragment mCurrentFragment;
    private String mCurrentFragmentClassName;

    private Toolbar mToolBar;
    private ActionBar mActionBar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;

    private LoginButton mFbLoginButton;
    private ImageView mFbLogoutButton;
    private View mFbProfileContainer;
    private CircleImageView mLoginUserAvatar;
    private TextView mLoginUserName;
    //private ProfilePictureView mProfilePictureView;


    public UIController(AppCompatActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void onCreate(Bundle savedInstanceState) {
        initialize(savedInstanceState);
    }

    private void initialize(Bundle savedInstanceState) {
        findViews();
        setupActionBar(savedInstanceState);
        setupViews();
        setupMainContent(savedInstanceState);
        setupDrawerLayout();
        setupLeftDrawer();
        setupRightDrawer();
        setupFacebook();

        // 如果要在開啟app的時候預先塞一些data到db中，設定好provider之後，可以在MainActivity的onCreate()ㄍ中加入
        //ContentValues values = new ContentValues();
        //values.put(DLExerciseContract.Task.TITLE, "123456");
        //values.put(DLExerciseContract.Task.DESCRIPTION, "123456 description");
        //values.put(DLExerciseContract.Task.TIME, System.currentTimeMillis());
        //mActivity.getContentResolver().insert(DLExerciseContract.Task.CONTENT_URI, values);
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
        mFbLogoutButton = (ImageView) headerLayout.findViewById(R.id.image_view_left_drawer_header_logout_button);
        mFbProfileContainer = headerLayout.findViewById(R.id.linear_layout_left_drawer_header_profile_container);
        mLoginUserAvatar =
                (CircleImageView) headerLayout.findViewById(R.id.circle_image_view_left_drawer_header_login_user_avatar);
        mLoginUserName = (TextView) headerLayout.findViewById(R.id.text_view_left_drawer_header_login_user_name);

        //mProfilePictureView = (ProfilePictureView) headerLayout.findViewById(R.id.profile_picture_view_left_drawer_header_login_user_avatar);
    }

    private void setupActionBar(Bundle savedState) {
        mActivity.setSupportActionBar(mToolBar);
        mActionBar = mActivity.getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(savedState == null ?
                    mActivity.getString(R.string.all_overview) :
                    savedState.getString(SavedStateKey.TITLE));
            //mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }

    private void setupViews() {
        mFbLogoutButton.setOnClickListener(this);
    }

    private void setupMainContent(Bundle savedState) {
        if (savedState == null) {
            mCurrentFragmentClassName = OverviewFragment.TAG;

        } else {
            mCurrentFragmentClassName =
                    savedState.getString(SavedStateKey.FRAGMENT_CLASS_NAME, OverviewFragment.TAG);
        }

        Log.d("danny", "Main content fragment class name = " + mCurrentFragmentClassName);

        try {
            // 以後Fragment的tag name都用此class的name來命名比較方便
            // e.g. MusicPlayerFragment
            //
            // 所以這邊我們可以直接拿Fragment Tag來new出一個Class object
            Fragment initFragment = FragmentUtils.getFragment(mFragmentManager,
                    (Class<? extends Fragment>) Class.forName(mCurrentFragmentClassName),
                    mCurrentFragmentClassName);

            FragmentUtils.addFragmentTo(mFragmentManager, initFragment, mCurrentFragmentClassName, R.id.frame_layout_main_container);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        // 差別在於有沒有傳Toolbar的object，如果這個Activity有用Toolbar取代掉原本的Action bar，記得要用有傳Toolbar的這種constructor
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
                        replaceContentFragment(OverviewFragment.class, OverviewFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_consuming_restful_web_service:
                        replaceContentFragment(ConsumingRestfulWebServiceFragment.class, ConsumingRestfulWebServiceFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_facebook:
                        replaceContentFragment(FacebookFragment.class, FacebookFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_do_it_later:
                        replaceContentFragment(DoItLaterFragment.class, DoItLaterFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_intent:
                        replaceContentFragment(IntentFragment.class, IntentFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_music_player:
                        replaceContentFragment(MusicPlayerFragment.class, MusicPlayerFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_dl_sdk:
                        replaceContentFragment(DLSdkFragment.class, DLSdkFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_firebase:
                        replaceContentFragment(FirebaseFragment.class, FirebaseFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_bitmap:
                        replaceContentFragment(BitmapFragment.class, BitmapFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_load_image_from_internet:
                        replaceContentFragment(LoadImageFromInternetFragment.class, LoadImageFromInternetFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_customized_view:
                        replaceContentFragment(CustomizedViewFragment.class, CustomizedViewFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_file:
                        replaceContentFragment(FileFragment.class, FileFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_chat:
                        replaceContentFragment(ChatFragment.class, ChatFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_time:
                        replaceContentFragment(TimeFragment.class, TimeFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_animation:
                        replaceContentFragment(AnimationFragment.class, AnimationFragment.TAG);
                        break;

                    case R.id.menu_item_left_drawer_test:
                        replaceContentFragment(TestFragment.class, TestFragment.TAG);
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
        Fragment rightDrawerFragment = FragmentUtils.getFragment(mFragmentManager,
                RightDrawerFragment.class,
                RightDrawerFragment.TAG);

        FragmentUtils.addFragmentTo(mFragmentManager, rightDrawerFragment, RightDrawerFragment.TAG,
                R.id.frame_layout_main_right_side_drawer);
    }

    public void replaceContentFragment(Class<? extends Fragment> fragmentClassToShow, String fragmentToShowTag) {
        try {
            Fragment fragmentToHide = FragmentUtils.getFragment(mFragmentManager,
                    (Class<? extends Fragment>) Class.forName(mCurrentFragmentClassName),
                    mCurrentFragmentClassName);

            Fragment fragmentToShow = FragmentUtils.getFragment(mFragmentManager, fragmentClassToShow, fragmentToShowTag);

            FragmentUtils.hideAndShowFragmentTo(mFragmentManager,
                    fragmentToHide, fragmentToShow,
                    fragmentToShowTag, R.id.frame_layout_main_container);

            mCurrentFragmentClassName = fragmentToShowTag;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isLeftDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private boolean isRightDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.END);
    }

    private void closeLeftDrawer() {
        if (!isLeftDrawerOpened()) {
            return;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void closeRightDrawer() {
        if (!isRightDrawerOpened()) {
            return;
        }

        mDrawerLayout.closeDrawer(GravityCompat.END);
    }

    private void openLeftDrawer() {
        if (isLeftDrawerOpened()) {
            return;
        }

        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void openRightDrawer() {
        if (isRightDrawerOpened()) {
            return;
        }

        mDrawerLayout.openDrawer(GravityCompat.END);
    }

    private void setupFacebook() {
        FacebookSdk.sdkInitialize(mActivity);
        mCallbackManager = CallbackManager.Factory.create();

        setupFbTrackers();
        setupFbLogin();
        setupFbLoginContentVisibility();
        setupFbProfile();
    }

    private void setupFbTrackers() {
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("danny", "Get new access token");

                AccessToken.setCurrentAccessToken(currentAccessToken);
                setupFbLoginContentVisibility();

                // 根據現在的access token來抓取profile資訊
                // On AccessToken changes fetch the new profile which fires the event on
                // the ProfileTracker if the profile is different
                Profile.fetchProfileForCurrentAccessToken();
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("danny", "Get new profile");

                Profile.setCurrentProfile(currentProfile);
                setupFbProfile();
            }
        };
    }

    private void setupFbLogin() {
        // Facebook SDK會在token要過期之前將token更新
        // 頻率為一天一次，在user有用app向FB server發出request的時候更新token
        // 但是如果超過60天沒有更新token，token就會過期，這時候就要重新再走一次login flow
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null || token.isExpired()) {
            Log.d("danny", "token is null");

            setupFbLoginButton();

        } else {
            Log.d("danny", token.getToken());
            Log.d("danny", "Approved permissions: " + token.getPermissions().toString());
            Log.d("danny", "Declined permissions: " + token.getDeclinedPermissions().toString());
            Log.d("danny", "Profile name = " + Profile.getCurrentProfile().getName());
            Log.d("danny", "Profile image uri = " + Profile.getCurrentProfile().getProfilePictureUri(200, 200));

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

    private void setupFbLoginContentVisibility() {
        if (AccessToken.getCurrentAccessToken() != null) {
            mFbProfileContainer.setVisibility(View.VISIBLE);
            mFbLoginButton.setVisibility(View.GONE);
            mFbLogoutButton.setVisibility(View.VISIBLE);

        } else {
            mFbProfileContainer.setVisibility(View.GONE);
            mFbLoginButton.setVisibility(View.VISIBLE);
            mFbLogoutButton.setVisibility(View.GONE);
        }
    }

    private void setupFbProfile() {
        if (Profile.getCurrentProfile() == null) {
            return;
        }

        // Login user name
        mLoginUserName.setText(Profile.getCurrentProfile().getName());

        // Login user image
        // Picasso: http://square.github.io/picasso/
        int widthHeight =
                mActivity.getResources().getInteger(R.integer.width_height_left_drawer_header_login_user_avatar_from_uri);
        Picasso.with(mActivity).load(Profile.getCurrentProfile().getProfilePictureUri(widthHeight, widthHeight))
                               .placeholder(R.drawable.ic_left_drawer_header_login_avatar).into(mLoginUserAvatar);

        // Profile image要拿剪裁過後的版本，還是原始版本
        //mProfilePictureView.setCropped(true);
        //mProfilePictureView.setProfileId(Profile.getCurrentProfile().getId());
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

    public boolean onCreateOptionsMenu(Menu menu) {
        mActivity.getMenuInflater().inflate(R.menu.main, menu);

        // 必須要return true，option menu才會show出來
        // return false可以將option menu hide起來
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // 使用DrawerToggle一定要完成此步驟
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        } else {
            switch (item.getItemId()) {
                case R.id.menu_item_main_open_right_drawer:
                    openRightDrawer();
                    return true;

                case R.id.menu_item_main_debug_database:
                    mActivity.startActivity(new Intent(mActivity, AndroidDatabaseManager.class));
                    return true;

                default:
                    return false;
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SavedStateKey.FRAGMENT_CLASS_NAME, mCurrentFragmentClassName);
        outState.putString(SavedStateKey.TITLE, String.valueOf(mActionBar.getTitle()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果有跳出別的視窗或是Activity，一定要實作CallbackManager及這個method，才可以運作正常
        // (保險起見，只要有用到Facebook API的地方，最後都要實作這步驟)
        // 必須要把onActivityResult()的結果傳給CallbackManager
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_left_drawer_header_logout_button:
                AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AlertDialogFragment.EXTRA_MESSAGE,
                                 mActivity.getString(R.string.left_drawer_header_sure_to_logout));

                alertDialogFragment.setArguments(bundle);
                alertDialogFragment.show(mFragmentManager, AlertDialogFragment.TAG);

                break;
        }
    }

    public void onClickAlertDialogOk() {
        logoutFb();
    }

    private void logoutFb() {
        LoginManager.getInstance().logOut();
    }

    public void onClickAlertDialogCancel() {

    }

    public void onCloseRightDrawer() {
        closeRightDrawer();
    }
}
