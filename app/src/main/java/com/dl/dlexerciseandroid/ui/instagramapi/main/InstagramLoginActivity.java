package com.dl.dlexerciseandroid.ui.instagramapi.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.InstagramApiUtils;

public class InstagramLoginActivity extends AppCompatActivity {

    private WebView mLoginWebView;


    private class AuthWebViewClient extends WebViewClient {

        // 讓使用這個WebView的app，當WebView的url重新load的時候，可以在這個callback method拿到
        // 如果沒有在WebView設定WebViewClient，系統會將這個url送給最適合處理的app(會跳出選app執行的視窗)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("danny", "Load url = " + url);

            // 帶有code的url會長這樣：http://localhost/?code=9b252f27d26742b48f716013a025f4fb
            // 我們判斷這個新load的url，前面是不是redirect_uri開頭，如果是的話代表這個是帶有code的url
            if (url.startsWith(getString(R.string.instagram_api_client_redirect_uri))) {
                String parts[] = url.split("=");

                sendCodeBack(parts[1]);
            }

            return false;
        }

        private void sendCodeBack(String code) {
            Intent intent = new Intent();
            intent.putExtra(InstagramMainActivity.EXTRA_INSTAGRAM_CODE, code);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_login);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupLoginWebView();
    }

    private void findViews() {
        mLoginWebView = (WebView) findViewById(R.id.web_view_instagram_api_login);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    private void setupLoginWebView() {
        // 一個WebView用來顯示Instagram的Authentication頁面
        mLoginWebView.setVerticalScrollBarEnabled(false);
        mLoginWebView.setHorizontalScrollBarEnabled(false);
        mLoginWebView.setWebViewClient(new AuthWebViewClient());
        mLoginWebView.getSettings().setJavaScriptEnabled(true);
        mLoginWebView.loadUrl(InstagramApiUtils.getAuthorizationUrl(this));
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
}
