package com.dl.dlexerciseandroid.ui.instagramapi.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGAccessTokenResponse;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGMedia;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGRecentMediaResponse;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGUser;
import com.dl.dlexerciseandroid.model.instagramapi.gson.IGUsersSelfResponse;
import com.dl.dlexerciseandroid.ui.instagramapi.feedview.FeedViewAdapter;
import com.dl.dlexerciseandroid.utility.utils.ApiUtils;
import com.dl.dlexerciseandroid.utility.utils.InstagramApiUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class InstagramMainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = InstagramMainActivity.class.getName();

    private static final int DEFAULT_RECENT_MEDIA_COUNT = 20;

    public static final String EXTRA_INSTAGRAM_CODE = "com.dl.dlexerciseandroid.EXTRA_INSTAGRAM_CODE";

    private static final int REQUEST_INSTAGRAM_LOGIN = 1;

    private ProgressBar mLoadingProgressBar;

    private Button mInstagramLoginButton;

    private ViewGroup mFeedContainer;
    private CircleImageView mLoginUserAvatarView;
    private TextView mLoginUserNameView;

    private RecyclerView mFeedView;
    private FeedViewAdapter mFeedViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_api);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupFeedView();
        tryTokenToLogin();
    }

    private void findViews() {
        mInstagramLoginButton = (Button) findViewById(R.id.button_instagram_api_login);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.progress_bar_instagram_api_loading);
        mFeedContainer = (ViewGroup) findViewById(R.id.view_group_instagram_api_feed_container);
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_instagram_api_login_user_avatar);
        mLoginUserNameView = (TextView) findViewById(R.id.text_view_instagram_api_login_user_name);
        mFeedView = (RecyclerView) findViewById(R.id.recycler_view_instagram_api_feed);
    }

    private void setupViews() {
        mInstagramLoginButton.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_instagram_api));
        }
    }

    private void setupFeedView() {
        mFeedViewAdapter = new FeedViewAdapter(this);

        mFeedView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFeedView.setAdapter(mFeedViewAdapter);
    }

    /**
     * 檢查SharedPreference中有沒有token的資訊：
     * 有：用get login user的API來檢查token有沒有過期
     * 沒有：跳出Authentication page
     */
    private void tryTokenToLogin() {
        if (InstagramDataCache.hasTokenInSharedPreference(this)) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);

            //new GetLoginUserAsyncTask(this, this).execute();
            fetchUsersSelf();

        } else {
            mInstagramLoginButton.setVisibility(View.VISIBLE);
            mLoadingProgressBar.setVisibility(View.GONE);
        }
    }

    private void fetchUsersSelf() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) throws Exception {
                String token = InstagramDataCache.getTokenFromSharedPreference(InstagramMainActivity.this);

                if (!TextUtils.isEmpty(token)) {
                    subscriber.onNext(token);
                    subscriber.onComplete();

                } else {
                    subscriber.onError(new Throwable());
                }
            }

        }).flatMap(new Function<String, ObservableSource<IGUsersSelfResponse>>() {
            @Override
            public ObservableSource<IGUsersSelfResponse> apply(String s) throws Exception {
                return ApiUtils.generateIGApi().getUsersSelf(s);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IGUsersSelfResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("danny", "fetchUsersSelf onSubscribe()");
                    }

                    @Override
                    public void onNext(IGUsersSelfResponse igUsersSelfResponse) {
                        Log.d("danny", "fetchUsersSelf Username = " + igUsersSelfResponse.getIGUser().getUserName());

                        InstagramDataCache.getInstance().setLoginUser(igUsersSelfResponse.getIGUser());
                    }

                    // Access token是null的狀況
                    @Override
                    public void onError(Throwable e) {
                        Log.d("danny", "Get login user failed");
                        Log.d("danny", "fetchUsersSelf onError()");

                        mInstagramLoginButton.setVisibility(View.VISIBLE);
                        mLoadingProgressBar.setVisibility(View.GONE);
                    }

                    /**
                     * GetLoginUserAsyncTask的callback，如果用SharedPreference中的token成功得到login user的資訊，
                     * 就直接顯示login user的資訊，並load feed
                     */
                    @Override
                    public void onComplete() {
                        Log.d("danny", "fetchUsersSelf onComplete()");

                        mInstagramLoginButton.setVisibility(View.GONE);
                        mLoadingProgressBar.setVisibility(View.VISIBLE);

                        setFeedContainer();
                    }
                });
    }

    private void setFeedContainer() {
        mFeedContainer.setVisibility(View.VISIBLE);

        setLoginUser();

        //new GetRecentMediaAsyncTask(this, this).execute();
        fetchRecentMedia();
    }

    private void fetchRecentMedia() {
        String token = InstagramDataCache.getTokenFromSharedPreference(InstagramMainActivity.this);

        if (TextUtils.isEmpty(token)) {
            return;
        }

        ApiUtils.generateIGApi()
                .getRecentMediaSelf("self", token, String.valueOf(DEFAULT_RECENT_MEDIA_COUNT))
                .doOnNext(new Consumer<IGRecentMediaResponse>() {
                    @Override
                    public void accept(IGRecentMediaResponse igRecentMediaResponse) throws Exception {
                        for (IGMedia igMedia : igRecentMediaResponse.getIGMedias()) {
                            igMedia.setRatio(igMedia.calculateRatio());

                            Log.d("danny", igMedia.toString());
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IGRecentMediaResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("danny", "fetchRecentMedia onSubscribe()");
                    }

                    @Override
                    public void onNext(IGRecentMediaResponse igRecentMediaResponse) {
                        mFeedViewAdapter.add(igRecentMediaResponse.getIGMedias());
                        mLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("danny", "fetchRecentMedia onError() = " + e.toString());

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("danny", "fetchRecentMedia onComplete()");
                    }
                });
    }

    private void setLoginUser() {
        if (InstagramDataCache.getInstance().getLoginUser() == null) {
            return;
        }

        Picasso.with(this).load(InstagramDataCache.getInstance().getLoginUser().getProfilePicture())
                .placeholder(R.drawable.ic_login_avatar).into(mLoginUserAvatarView);
        mLoginUserNameView.setText(InstagramDataCache.getInstance().getLoginUser().getFullName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 沒有access_token，需要執行拿取access_token的步驟
            case R.id.button_instagram_api_login:
                startActivityForResult(new Intent(InstagramMainActivity.this, InstagramLoginActivity.class), REQUEST_INSTAGRAM_LOGIN);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_INSTAGRAM_LOGIN:
                // resultCode有兩種：
                // RESULT_OK: 在另一個Activity的operation有成功完成
                // RESULT_CANCELED: operation沒有成功完成，或是user直接退出Activity
                if (RESULT_OK == resultCode) {
                    Log.d("danny", "onActivityResult RESULT_OK");

                    if (data == null) {
                        break;
                    }

                    mLoadingProgressBar.setVisibility(View.GONE);

                    // 拿到要存取access_token必須要的code
                    String code = data.getStringExtra(EXTRA_INSTAGRAM_CODE);

                    Log.d("danny", "Instagram code = " + code);

                    //new GetAuthenticationTokenAsyncTask(InstagramMainActivity.this, this).execute(code);
                    fetchAccessToken(code);

                } else if (RESULT_CANCELED == resultCode) {
                    Log.d("danny", "onActivityResult RESULT_CANCELED");

                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    private void fetchAccessToken(String code) {
        ApiUtils.generateIGAccessTokenApi()
                .getAccessToken(InstagramApiUtils.getTokenBodyMap(this, code))
                .doOnNext(new Consumer<IGAccessTokenResponse>() {
                    @Override
                    public void accept(IGAccessTokenResponse igAccessTokenResponse) throws Exception {
                        saveTokenAndLoginUser(igAccessTokenResponse.getAccessToken(),
                                                     igAccessTokenResponse.getIGUser());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IGAccessTokenResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("danny", "fetchAccessToken onSubscribe()");
                    }

                    @Override
                    public void onNext(IGAccessTokenResponse igAccessTokenResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("danny", "fetchAccessToken failed: " + e.toString());

                        mInstagramLoginButton.setVisibility(View.VISIBLE);
                        mLoadingProgressBar.setVisibility(View.GONE);
                    }

                    /**
                     * GetAuthenticationTokenAsyncTask的callback，如果成功get token，
                     * 就直接顯示login user的資訊，並load feed
                     */
                    @Override
                    public void onComplete() {
                        Log.d("danny", "fetchAccessToken onComplete()");
                        Log.d("danny", "Token = " + InstagramDataCache.getTokenFromSharedPreference(InstagramMainActivity.this));
                        Log.d("danny", "Login user = " + InstagramDataCache.getInstance().getLoginUser().toString());

                        mInstagramLoginButton.setVisibility(View.GONE);
                        mLoadingProgressBar.setVisibility(View.VISIBLE);

                        setFeedContainer();
                    }
                });
    }

    private void saveTokenAndLoginUser(String token, IGUser loginUser) {
        InstagramDataCache.saveTokenToSharedPreference(this, token);
        InstagramDataCache.getInstance().setLoginUser(loginUser);
    }

//    /**
//     * GetAuthenticationTokenAsyncTask的callback，如果成功get token，
//     * 就直接顯示login user的資訊，並load feed
//     */
//    @Override
//    public void onGetAuthenticationTokenSuccessful() {
//        Log.d("danny", "Token = " + InstagramDataCache.getTokenFromSharedPreference(this));
//        Log.d("danny", "Login user = " + InstagramDataCache.getInstance().getLoginUser().toString());
//
//        mInstagramLoginButton.setVisibility(View.GONE);
//        mLoadingProgressBar.setVisibility(View.VISIBLE);
//
//        setFeedContainer();
//    }

//    @Override
//    public void onGetAuthenticationTokenFailed() {
//        Log.d("danny", "Get authentication token failed");
//
//        mInstagramLoginButton.setVisibility(View.VISIBLE);
//        mLoadingProgressBar.setVisibility(View.GONE);
//    }

//    /**
//     * GetLoginUserAsyncTask的callback，如果用SharedPreference中的token成功得到login user的資訊，
//     * 就直接顯示login user的資訊，並load feed
//     */
//    @Override
//    public void onGetLoginUserSuccessful() {
//        mInstagramLoginButton.setVisibility(View.GONE);
//        mLoadingProgressBar.setVisibility(View.VISIBLE);
//
//        setFeedContainer();
//    }
//
//    @Override
//    public void onGetLoginUserFailed() {
//        Log.d("danny", "Get login user failed");
//
//        mInstagramLoginButton.setVisibility(View.VISIBLE);
//        mLoadingProgressBar.setVisibility(View.GONE);
//    }

//    @Override
//    public void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia) {
//        if (igRecentMedia.getImageList() == null || igRecentMedia.getImageList().size() == 0) {
//            return;
//        }
//
//        mFeedViewAdapter.add(igRecentMedia.getImageList());
//        mLoadingProgressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onGetRecentMediaFailed() {
//        Log.d("danny", "onGetRecentMediaFailed()");
//    }
}
