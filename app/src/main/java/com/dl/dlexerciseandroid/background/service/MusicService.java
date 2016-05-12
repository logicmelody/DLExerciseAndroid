package com.dl.dlexerciseandroid.background.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.RemoteViews;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    public static final class Action {
        public static final String START_PLAYING_MUSIC = "com.dl.dlexerciseandroid.ACTION_START_PLAYING_MUSIC";
        public static final String STOP_PLAYING_MUSIC = "com.dl.dlexerciseandroid.ACTION_STOP_PLAYING_MUSIC";
        public static final String CHANGE_MUSIC = "com.dl.dlexerciseandroid.ACTION_CHANGE_MUSIC";
        public static final String PREVIOUS_MUSIC = "com.dl.dlexerciseandroid.ACTION_PREVIOUS_MUSIC";
        public static final String NEXT_MUSIC = "com.dl.dlexerciseandroid.ACTION_NEXT_MUSIC";
        public static final String PAUSE_MUSIC = "com.dl.dlexerciseandroid.ACTION_PAUSE_MUSIC";
        public static final String RESUME_MUSIC = "com.dl.dlexerciseandroid.ACTION_RESUME_MUSIC";
    }

    public static final class ExtraKey {
        public static final String EXTRA_MUSIC_LIST = "com.dl.dlexerciseandroid.EXTRA_MUSIC_LIST";
        public static final String EXTRA_CURRENT_MUSIC_POSITION = "com.dl.dlexerciseandroid.EXTRA_CURRENT_MUSIC_POSITION";
    }

    public static final int MUSIC_NOTIFICATION_ID = 100;

    // 用來與其他component做溝通的class，其他component可以藉由這個class得到MusicService的instance，
    // 進而使用我們定義好的一些屬於MusicService的public method
    public class MusicBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }

    NotificationManager mNotificationManager;

    // 要回傳給component的Binder物件
    private final IBinder mMusicBinder = new MusicBinder();

    private MediaPlayer mPlayer;
    private List<Music> mMusicList;

    private int mCurrentMusicPosition = -1;

    private boolean mIsLooped = true;
    private boolean mIsShuffle = false;


    public static Intent generateStartPlayingMusicIntent(Context context, ArrayList<Music> musicList,
                                                         int currentMusicPosition) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(Action.START_PLAYING_MUSIC);
        intent.putExtra(ExtraKey.EXTRA_MUSIC_LIST, musicList);
        intent.putExtra(ExtraKey.EXTRA_CURRENT_MUSIC_POSITION, currentMusicPosition);

        return intent;
    }

    // 開始播放一首music，根據新的mCurrentMusicPosition來setDataSource()
    // Note: 每次要換首音樂播放都一定要call這個method
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

    public void playPreviousMusic(){
        if (mIsShuffle) {
            mCurrentMusicPosition = getRandomMusicPosition();

        } else {
            mCurrentMusicPosition--;

            if (mCurrentMusicPosition < 0) {
                mCurrentMusicPosition = mMusicList.size() - 1;
            }
        }

        playMusic();
    }

    private int getRandomMusicPosition() {
        int newMusicPosition = mCurrentMusicPosition;
        while (newMusicPosition == mCurrentMusicPosition) {
            newMusicPosition = new Random().nextInt(mMusicList.size());
        }

        return newMusicPosition;
    }

    public void playNextMusic(){
        if (mIsShuffle) {
            mCurrentMusicPosition = getRandomMusicPosition();

        } else {
            mCurrentMusicPosition++;

            if(mIsLooped && mCurrentMusicPosition >= mMusicList.size()) {
                mCurrentMusicPosition = 0;
            }
        }

        if (mCurrentMusicPosition >= mMusicList.size()) {
            // Handle end of loop

            return;
        }

        playMusic();
    }

    // Play -> pause
    public void pauseMusic(){
        mPlayer.pause();
    }

    public int getPosition(){
        return mPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mPlayer.getDuration();
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

    public void seek(int position){
        mPlayer.seekTo(position);
    }

    // Pause -> play
    public void resumeMusic(){
        mPlayer.start();
    }

    public void setLooped(boolean isLooped) {
        mIsLooped = isLooped;
    }

    public void setShuffle(boolean isShuffle) {
        mIsShuffle = isShuffle;
    }

    // 我們利用getPlayingMusic()來得到現在正在播放music的資訊，除此之外，也可以利用此method來檢查MusicService是不是已經開始播放音樂
    // 因為我們播放音樂的啟動點是user點選list中的一首音樂，這樣就一定會傳music的position還有music list的資訊給MusicService
    public Music getPlayingMusic() {
        if (mMusicList == null || mCurrentMusicPosition == -1) {
            return null;
        }

        return mMusicList.get(mCurrentMusicPosition);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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

    // 用這個method來指定service要執行哪件工作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (Action.START_PLAYING_MUSIC.equals(action)) {
            // 在每次user choose一首歌的時候，我們就把最新的music list和chosen music的position透過intent傳給Service
            // 會走到onPrepared()
            startPlayingMusic(intent);

        } else if (Action.STOP_PLAYING_MUSIC.equals(action)) {
            stopPlayingMusic();

        } else if (Action.PREVIOUS_MUSIC.equals(action)) {
            // 會走到onPrepared()
            playPreviousMusic();

        } else if (Action.RESUME_MUSIC.equals(action)) {
            // 不會走到onPrepared()
            resumeMusic();
            setNotification();

        } else if (Action.PAUSE_MUSIC.equals(action)) {
            // 不會走到onPrepared()
            pauseMusic();
            setNotification();

        } else if (Action.NEXT_MUSIC.equals(action)) {
            // 會走到onPrepared()
            playNextMusic();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startPlayingMusic(Intent intent) {
        // 得到現在music list中所有的music基本資訊
        mMusicList = intent.getParcelableArrayListExtra(ExtraKey.EXTRA_MUSIC_LIST);

        // 如果user點選了一個已經正在播放的music，則不做任何事情
        int passedPosition = intent.getIntExtra(ExtraKey.EXTRA_CURRENT_MUSIC_POSITION, 0);
        if (mCurrentMusicPosition == passedPosition) {
            return;
        }

        mCurrentMusicPosition = passedPosition;

        Log.d("danny", "Play music: " + mMusicList.get(mCurrentMusicPosition).title);

        // Play music
        playMusic();
    }

    private void stopPlayingMusic() {
        Log.d("danny", "MusicService stopPlayingMusic()");

        stopSelf();
        sendBroadcast(new Intent(Action.STOP_PLAYING_MUSIC));
    }

    // 用這個method回傳binder給component，讓component可以拿到一些service的information
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
        mCurrentMusicPosition = -1;
        mMusicList = null;

        super.onDestroy();
    }

    // Media source已經準備好，隨時可以播放
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

        // 不管是user自己手動click button來換下一首音樂，或是目前這首音樂已經播放結束，自動切換下一首音樂
        // 都一定會執行onPrepared()這段code，所以我們在這裡面發送change music的broadcast intent通知MusicControllerActivity
        // 要更新顯示的music information
        sendChangeMusicBroadcast();

        // 在onPrepared()結束要開始播放音樂時，更新notification，
        setNotification();
    }

    private void sendChangeMusicBroadcast() {
        sendBroadcast(new Intent(Action.CHANGE_MUSIC));
    }

    private void setNotification() {
        // 用來建造notification的builder
        Notification.Builder builder = new Notification.Builder(this);

        // 一個notification一定要有以下三個資訊：
        // 1. setSmallIcon()：在status bar上出現的小icon
        // 2. setContentTitle()
        // 3. setContentText()
        // Note: 如果使用setContent()來使用customized layout的話，2跟3項可以不需要
        builder.setSmallIcon(R.drawable.ic_statuc_bar_playing_music)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MAX)

                // 用setContent()可以定義自己的notification layout
                .setContent(generatePlayingMusicNotificationView());

        // Notification要送出時，如果都是用同一個notification id，這樣舊的會被新的update
        // 我們希望MusicService可以一直在background與user互動，可以用startForeground()與setOngoing(true)搭配
        startForeground(MUSIC_NOTIFICATION_ID, builder.build());
    }

    private RemoteViews generatePlayingMusicNotificationView() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                                                  R.layout.layout_notification_playing_music);

        // Previous music
        Intent previousMusicIntent = new Intent(this, MusicService.class);
        previousMusicIntent.setAction(Action.PREVIOUS_MUSIC);
        setOnClickPendingIntent(remoteViews, R.id.image_view_notification_previous_music, previousMusicIntent);

        if (isPlaying()) {
            // Pause music
            Intent pauseMusicIntent = new Intent(this, MusicService.class);
            pauseMusicIntent.setAction(Action.PAUSE_MUSIC);
            setOnClickPendingIntent(remoteViews, R.id.image_view_notification_resume_or_pause, pauseMusicIntent);

        } else {
            // Resume music
            Intent resumeMusicIntent = new Intent(this, MusicService.class);
            resumeMusicIntent.setAction(Action.RESUME_MUSIC);
            setOnClickPendingIntent(remoteViews, R.id.image_view_notification_resume_or_pause, resumeMusicIntent);
        }

        // Next music
        Intent nextMusicIntent = new Intent(this, MusicService.class);
        nextMusicIntent.setAction(Action.NEXT_MUSIC);
        setOnClickPendingIntent(remoteViews, R.id.image_view_notification_next_music, nextMusicIntent);

        // Stop playing music
        Intent stopPlayingMusicIntent = new Intent(this, MusicService.class);
        stopPlayingMusicIntent.setAction(Action.STOP_PLAYING_MUSIC);
        setOnClickPendingIntent(remoteViews, R.id.image_view_notification_stop_playing_music, stopPlayingMusicIntent);

        // Playing music title
        remoteViews.setTextViewText(R.id.text_view_notification_playing_music_title,
                                    mMusicList.get(mCurrentMusicPosition).title);

        if (isPlaying()) {
            remoteViews.setImageViewResource(R.id.image_view_notification_resume_or_pause,
                                             R.drawable.ic_music_controller_pause);

        } else {
            remoteViews.setImageViewResource(R.id.image_view_notification_resume_or_pause,
                                             R.drawable.ic_music_controller_play);
        }

        return remoteViews;
    }

    private void setOnClickPendingIntent(RemoteViews remoteViews, int id, Intent intent) {
        remoteViews.setOnClickPendingIntent(id, PendingIntent.getService(this, 0, intent, 0));
    }

    // Media source已經播放完畢時
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!mIsShuffle && !mIsLooped && mCurrentMusicPosition == mMusicList.size() - 1) {
            // Handle end of loop

            return;
        }

        playNextMusic();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
