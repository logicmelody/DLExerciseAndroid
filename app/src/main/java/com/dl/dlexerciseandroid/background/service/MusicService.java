package com.dl.dlexerciseandroid.background.service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dl.dlexerciseandroid.datastructure.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    public static final class Action {
        public static final String START_PLAYING_MUSIC = "com.dl.dlexerciseandroid.ACTION_START_PLAYING_MUSIC";
    }

    public static final class ExtraKey {
        public static final String EXTRA_MUSIC_LIST = "com.dl.dlexerciseandroid.EXTRA_MUSIC_LIST";
        public static final String EXTRA_CURRENT_MUSIC_POSITION = "com.dl.dlexerciseandroid.EXTRA_CURRENT_MUSIC_POSITION";
    }

    // 用來與其他component做溝通的class，其他component可以藉由這個class得到MusicService的instance，
    // 進而使用我們定義好的一些屬於MusicService的public method
    public class MusicBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }

    // 要回傳給component的Binder物件
    private final IBinder mMusicBinder = new MusicBinder();

    private MediaPlayer mPlayer;
    private List<Music> mMusicList;

    private Music mCurrentMusic;
    private int mCurrentMusicPosition = 0;


    public static Intent generateStartPlayingMusicIntent(Context context, ArrayList<Music> musicList,
                                                         int currentMusicPosition) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(Action.START_PLAYING_MUSIC);
        intent.putExtra(ExtraKey.EXTRA_MUSIC_LIST, musicList);
        intent.putExtra(ExtraKey.EXTRA_CURRENT_MUSIC_POSITION, currentMusicPosition);

        return intent;
    }

    // 根據新的mCurrentMusicPosition來setDataSource()
    // Note: 每次要換首歌播放都一定要call這個method
    public void playMusic() {
        mPlayer.reset();

        Uri musicUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                                  mMusicList.get(mCurrentMusicPosition).id);

        try{
            mPlayer.setDataSource(this, musicUri);

        } catch(Exception e){
            Log.e("MusicService", "Error setting data source", e);
        }

        // setDataSource()之後，要call prepare()或是prepareAsync()，如果是for streams，必須call prepareAsync()
        mPlayer.prepareAsync();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        mPlayer = new MediaPlayer();
        setupMusicPlayer();
    }

    private void setupMusicPlayer() {
        // If the user presses the power button, then the screen will be turned off
        // but the CPU will be kept on until all partial wake locks have been released.
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

        // 我們的music檔案來源是device上的檔案(local URI)或是remote URI from internet
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        // 在每次user choose一首歌的時候，我們就把最新的music list和chosen music的position透過intent傳給Service
        if (Action.START_PLAYING_MUSIC.equals(action)) {
            startPlayingMusic(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startPlayingMusic(Intent intent) {
        // Set music
        mMusicList = intent.getParcelableArrayListExtra(ExtraKey.EXTRA_MUSIC_LIST);
        mCurrentMusicPosition = intent.getIntExtra(ExtraKey.EXTRA_CURRENT_MUSIC_POSITION, 0);
        mCurrentMusic = mMusicList.get(mCurrentMusicPosition);

        Log.d("danny", "Play music: " + mCurrentMusic.title);

        // Play music
        playMusic();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("danny", "MusicService onBind()");

        return mMusicBinder;
    }

    // unbind的時候，我們不會把mPlayer給release掉，因為就算MusicControllerActivity這個client消失了，music還是應該繼續播放
    // 所以正確release mPlayer的地方，應該是onDestroy()的時候
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("danny", "MusicService onUnbind()");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("danny", "MusicService onDestroy()");

        mPlayer.stop();
        mPlayer.release();

        super.onDestroy();
    }

    // Media source已經準備好，隨時可以播放
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    // Media source已經播放完畢時
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
