package com.dl.dlexerciseandroid.musicplayer.musiccontroller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.service.MusicService;
import com.dl.dlexerciseandroid.datastructure.Music;

// 這個class主要用來顯示現在正在播放音樂的一些基本資訊：歌名, 歌手
// 與service bind完成之後，也可以經由controller的一些button操作MusicService：上一首, 下一首, play, pause, ......
public class MusicControllerActivity extends AppCompatActivity implements View.OnClickListener,
        MusicControlReceiver.OnMusicControlListener {

    public static final String EXTRA_PLAYING_MUSIC = "com.dl.dlexerciseandroid.EXTRA_PLAYING_MUSIC";

    private static final String PREFERENCE_SHUFFLE = "com.dl.dlexerciseandroid.PREFERENCE_SHUFFLE";
    private static final String PREFERENCE_LOOP = "com.dl.dlexerciseandroid.PREFERENCE_LOOP";

    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private TextView mMusicTitle;
    private TextView mMusicArtist;

    private ImageView mShuffle;
    private ImageView mPrevious;
    private ImageView mPlayOrPause;
    private ImageView mNext;
    private ImageView mLoop;

    private MusicService mMusicService;

    private MusicControlReceiver mMusicControlReceiver;

    private Music mPlayingMusic;

    private boolean mIsMusicServiceBound = false;

    // 與MusicService建立連線來取得binder物件
    private ServiceConnection mMusicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("danny", "Bind MusicService from MusicControllerActivity");

            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;

            // Get service
            mMusicService = binder.getService();
            mIsMusicServiceBound = true;

            setPlayOrPauseIcon();

            // 我們統一由這個MusicController來控制是否loop和shuffle，
            // 同時我們把這些資訊存在那個button的tag中
            mMusicService.setLooped((boolean) mLoop.getTag());
            mMusicService.setShuffle((boolean) mShuffle.getTag());
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
        // 為了要讓MusicControllerActivity出現的時候馬上就可以顯示播放音樂的基本資訊，而不需要等到bind完成之後才顯示，
        // 啟動此Activity的時候需要傳一個music object
        // 我們啟動的地方有兩個：
        // 1. 在MusicListAdapter中，user點選一個music item：從music list可以得到music object
        // 2. user點選playing music item：那個時候MusicPlayerFragment已經與MusicService bind完成，所以可以透過
        //    mMusicService.getPlayingMusic()得到
        mPlayingMusic = getIntent().getParcelableExtra(EXTRA_PLAYING_MUSIC);
        mMusicControlReceiver = new MusicControlReceiver(this);

        findViews();
        setupActionBar();
        setPlayingMusicInfo();
        setupMusicController();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mMusicTitle = (TextView) findViewById(R.id.text_view_music_controller_title);
        mMusicArtist = (TextView) findViewById(R.id.text_view_music_controller_artist);
        mShuffle = (ImageView) findViewById(R.id.image_view_music_controller_shuffle);
        mPrevious = (ImageView) findViewById(R.id.image_view_music_controller_previous);
        mPlayOrPause = (ImageView) findViewById(R.id.image_view_music_controller_play_or_pause);
        mNext = (ImageView) findViewById(R.id.image_view_music_controller_next);
        mLoop = (ImageView) findViewById(R.id.image_view_music_controller_loop);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setTitle("");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setPlayingMusicInfo() {
        if (mIsMusicServiceBound) {
            mPlayingMusic = mMusicService.getPlayingMusic();
        }

        mMusicTitle.setText(mPlayingMusic.title);
        mMusicArtist.setText(mPlayingMusic.artist);
    }

    private void setupMusicController() {
        mShuffle.setTag(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCE_SHUFFLE, false));
        mLoop.setTag(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCE_LOOP, true));
        setShuffleIcon((boolean) mShuffle.getTag());
        setLoopIcon((boolean) mLoop.getTag());

        mShuffle.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
        mPlayOrPause.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mLoop.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register從MusicService來的change music broadcast intent
        IntentFilter intentFilter = new IntentFilter(MusicService.Action.CHANGE_MUSIC);
        IntentFilter intentFilter2 = new IntentFilter(MusicService.Action.STOP_PLAYING_MUSIC);

        registerReceiver(mMusicControlReceiver, intentFilter);
        registerReceiver(mMusicControlReceiver, intentFilter2);

        // 這個MusicControllerActivity當作client來與MusicService做bind的動作，來取得現在正在播放music的一些資訊，顯示在UI上
        // Note: 在執行到這步以前，MusicService已經在choose music的時候startService()了，所以這個Activity unbind之後，
        //       MusicService並不會destroy，還必須call stopService()或是stopSelf()才可以把MusicService關掉
        bindService(new Intent(this, MusicService.class), mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(mMusicControlReceiver);
        unbindMusicService();

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean(PREFERENCE_SHUFFLE, (boolean) mShuffle.getTag());
        editor.putBoolean(PREFERENCE_LOOP, (boolean) mLoop.getTag());
        editor.apply();
    }

    // 因為unbind service可能會走onStopPlayingMusic()這條路，而不是一般正常的onStop()，
    // 如果在onStopPlayingMusic()已經unbind，而在onStop()又unbind一次，app會crash
    // 所以我們寫一個method：在unbind之前會先檢查component有沒有跟service bind在一起
    private void unbindMusicService() {
        if (!mIsMusicServiceBound) {
            return;
        }

        Log.d("danny", "Unbind MusicService from MusicControllerActivity");

        mIsMusicServiceBound = false;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_music_controller_shuffle:
                shuffle();

                break;

            case R.id.image_view_music_controller_previous:
                playPrevious();

                break;

            case R.id.image_view_music_controller_play_or_pause:
                playOrPause();

                break;

            case R.id.image_view_music_controller_next:
                playNext();

                break;

            case R.id.image_view_music_controller_loop:
                loop();

                break;
        }
    }

    private void shuffle() {
        if (!mIsMusicServiceBound) {
            return;
        }

        boolean isShuffle = (boolean) mShuffle.getTag();

        setShuffleIcon(!isShuffle);
        mShuffle.setTag(!isShuffle);
        mMusicService.setShuffle(!isShuffle);
    }

    private void setShuffleIcon(boolean isShuffle) {
        if (isShuffle) {
            mShuffle.setImageResource(R.drawable.ic_music_controller_shuffle_highlight);

        } else {
            mShuffle.setImageResource(R.drawable.ic_music_controller_shuffle);
        }
    }

    private void playPrevious() {
        if (!mIsMusicServiceBound) {
            return;
        }

        mMusicService.playPreviousMusic();
        mPlayOrPause.setImageResource(R.drawable.ic_music_controller_pause);
    }

    private void playOrPause() {
        if (!mIsMusicServiceBound) {
            return;
        }

        // Play -> pause
        if (mMusicService.isPlaying()) {
            mMusicService.pauseMusic();

        // Pause -> play
        } else {
            mMusicService.resumeMusic();
        }

        setPlayOrPauseIcon();
    }

    private void setPlayOrPauseIcon() {
        if (mMusicService.isPlaying()) {
            mPlayOrPause.setImageResource(R.drawable.ic_music_controller_pause);

        } else {
            mPlayOrPause.setImageResource(R.drawable.ic_music_controller_play);
        }
    }

    private void playNext() {
        if (!mIsMusicServiceBound) {
            return;
        }

        mMusicService.playNextMusic();
        mPlayOrPause.setImageResource(R.drawable.ic_music_controller_pause);
    }

    private void loop() {
        if (!mIsMusicServiceBound) {
            return;
        }

        boolean isLoop = (boolean) mLoop.getTag();

        setLoopIcon(!isLoop);
        mLoop.setTag(!isLoop);
        mMusicService.setLooped(!isLoop);
    }

    private void setLoopIcon(boolean isLoop) {
        if (isLoop) {
            mLoop.setImageResource(R.drawable.ic_music_controller_loop_highlight);

        } else {
            mLoop.setImageResource(R.drawable.ic_music_controller_loop);
        }
    }

    @Override
    public void onChangeMusic() {
        setPlayingMusicInfo();
    }

    // 如果是從notification關掉MusicService，會呼叫這個callback
    // 在這邊我們將MusicControllerActivity與service unbind，並且把Activity關掉
    @Override
    public void onStopPlayingMusic() {
        unbindMusicService();
        finish();
    }
}
