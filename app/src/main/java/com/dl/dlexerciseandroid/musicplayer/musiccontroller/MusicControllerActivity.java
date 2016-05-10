package com.dl.dlexerciseandroid.musicplayer.musiccontroller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.service.MusicService;

public class MusicControllerActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private MusicService mMusicService;

    private boolean mIsMusicServiceBound = false;

    private ServiceConnection mMusicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("danny", "Bind MusicService from MusicControllerActivity");

            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;

            // Get service
            mMusicService = binder.getService();
            mIsMusicServiceBound = true;
        }

        // 這個method在service crash或是service被系統kill掉的時候會被呼叫，如果是client unbind則不會呼叫此method
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsMusicServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_controller);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setTitle("");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 這個MusicControllerActivity當作client來與MusicService做bind的動作，來取得現在正在播放music的一些資訊，顯示在UI上
        // Note: 在執行到這步以前，MusicService已經在choose music的時候startService()了，所以這個Activity unbind之後，
        //       MusicService並不會destroy，還必須call stopService()或是stopSelf()才可以把MusicService關掉
        bindService(new Intent(this, MusicService.class), mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("danny", "Unbind MusicService from MusicControllerActivity");

        // 當此Activity消失的時候要記得與MusicService unbind
        unbindService(mMusicConnection);
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
