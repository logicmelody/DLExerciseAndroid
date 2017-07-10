package com.dl.dlexerciseandroid.ui.instagramapi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;

public class InstagramMainActivity extends AppCompatActivity implements
        View.OnClickListener, GetAuthenticationTokenAsyncTask.OnGetAuthenticationTokenListener {

    private static final String TAG = InstagramMainActivity.class.getName();

    public static final String EXTRA_INSTAGRAM_CODE = "com.dl.dlexerciseandroid.EXTRA_INSTAGRAM_CODE";

    private static final int REQUEST_INSTAGRAM_LOGIN = 1;

    private Button mInstagramLoginButton;


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
    }

    private void findViews() {
        mInstagramLoginButton = (Button) findViewById(R.id.button_instagram_api_login);
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

                    // 拿到要存取access_token必須要的code
                    String code = data.getStringExtra(EXTRA_INSTAGRAM_CODE);

                    Log.d("danny", "Instagram code = " + code);

                    new GetAuthenticationTokenAsyncTask(InstagramMainActivity.this, this).execute(code);

                } else if (RESULT_CANCELED == resultCode) {
                    Log.d("danny", "onActivityResult RESULT_CANCELED");
                }

                break;
        }
    }

    @Override
    public void onGetAuthenticationTokenSuccessful() {
        Log.d("danny", "Token = " + InstagramDataCache.getInstance().getToken());
        Log.d("danny", "Login user = " + InstagramDataCache.getInstance().getLoginUser().toString());
    }

    @Override
    public void onGetAuthenticationTokenFailed() {
        Log.d("danny", "Get authentication token failed");
    }
}
