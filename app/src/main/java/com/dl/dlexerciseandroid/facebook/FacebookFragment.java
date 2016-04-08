package com.dl.dlexerciseandroid.facebook;

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
import com.dl.dlexerciseandroid.utility.FbUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by logicmelody on 2016/4/8.
 */
public class FacebookFragment extends Fragment {

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

        // AccessToken沒有更新：要搭配AccessTokenTracker來track最新的token
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
                    LoginManager.getInstance().logInWithReadPermissions(FacebookFragment.this,
                                                                        Arrays.asList(FbUtils.Permission.USER_FRIENDS));

                } else {
                    // Switch is off
                }
            }
        });
    }

    private void setSwitch(Switch s, String tag) {
        Set<String> permissionSet = AccessToken.getCurrentAccessToken().getPermissions();
        Log.d("danny", "setSwitch " + tag + " " + permissionSet.contains(tag));

        s.setChecked(permissionSet.contains(tag));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
