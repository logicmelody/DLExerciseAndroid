package com.dl.dlexerciseandroid.ui.facebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.FbUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by logicmelody on 2016/4/8.
 */
public class FacebookFragment extends Fragment {

    public static final String TAG = FacebookFragment.class.getName();

    private Context mContext;

    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    private Switch mUserFriendsSwitch;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_facebook, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupFacebook();
        setupSwitches();
    }

    private void findViews() {
        mUserFriendsSwitch = (Switch) getView().findViewById(R.id.switch_facebook_user_friends);
    }

    private void setupFacebook() {
        mCallbackManager = CallbackManager.Factory.create();

        // 如果要拿到別的視窗或是Activity回傳回來的callback(ex: permission視窗)，
        // 可以實作這個callback method
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("danny", "LoginManager onSuccess");
            }

            @Override
            public void onCancel() {
                Log.d("danny", "LoginManager onCancel");

                setSwitch(mUserFriendsSwitch, FbUtils.Permission.USER_FRIENDS);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("danny", "LoginManager onError");
            }
        });

        // AccessToken沒有更新：要搭配AccessTokenTracker來track最新的token
        // 當我們access token或是profile改變的時候，會呼叫tracker裡面的callback method
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("danny", "New AccessToken is coming");

                AccessToken.setCurrentAccessToken(currentAccessToken);
                setSwitch(mUserFriendsSwitch, FbUtils.Permission.USER_FRIENDS);
            }
        };

        mAccessTokenTracker.startTracking();
    }

    private void setupSwitches() {
        setSwitch(mUserFriendsSwitch, FbUtils.Permission.USER_FRIENDS);

        mUserFriendsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch is on

                    // Request permission: user_friends
                    LoginManager.getInstance().logInWithReadPermissions(FacebookFragment.this,
                                                                        Arrays.asList(FbUtils.Permission.USER_FRIENDS));

                } else {
                    // Switch is off
                    if (FbUtils.hasPermission(FbUtils.Permission.USER_FRIENDS)) {
                        // Revoke permission: user_friends
                        // Uri: /{user-id}/permissions/{permission-name}
                        new GraphRequest(AccessToken.getCurrentAccessToken(),
                                FbUtils.getRevokingPermissionUri(FbUtils.Permission.USER_FRIENDS),
                                null,
                                HttpMethod.DELETE,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                        Log.d("danny", "Delete permission user_friends");

                                        // 這邊需要手動更新，不然不會call onCurrentAccessTokenChanged()，
                                        // 可能是因為不是從別的視窗或是Activity回傳結果回來，
                                        // 所以沒有call CallbackManager的onActivityResult()

                                        // 因為更新了permission，會有新的token，所以會呼叫onCurrentAccessTokenChanged()
                                        AccessToken.refreshCurrentAccessTokenAsync();
                                    }
                                }
                        ).executeAsync();
                    }
                }
            }
        });
    }

    private void setSwitch(Switch s, String permission) {
        if (AccessToken.getCurrentAccessToken() == null) {
            mUserFriendsSwitch.setEnabled(false);

            return;
        }

        boolean hasPermission = FbUtils.hasPermission(permission);
        Log.d("danny", "setSwitch " + permission + " " + hasPermission);

        mUserFriendsSwitch.setEnabled(true);
        s.setChecked(hasPermission);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 如果有跳出別的視窗或是Activity，一定要實作CallbackManager及這個method，才可以運作正常
        // 必須要把onActivityResult()的結果傳給CallbackManager
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
    }
}
