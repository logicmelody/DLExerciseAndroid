package com.dl.dlexerciseandroid.features.musicplayer.musiccontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dl.dlexerciseandroid.backgroundtask.service.MusicService;

/**
 * Created by logicmelody on 2016/5/11.
 */

// 此receiver用來接收以下broadcast intent
// 1. 音樂切換的時候
// 2. 從notification停止MusicService的時候
public class MusicControlReceiver extends BroadcastReceiver {

    public interface OnMusicControlListener {
        void onChangeMusic();
        void onStopPlayingMusic();
    }

    private OnMusicControlListener mOnMusicControlListener;


    public MusicControlReceiver(OnMusicControlListener listener) {
        mOnMusicControlListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (MusicService.Action.CHANGE_MUSIC.equals(action)) {
            mOnMusicControlListener.onChangeMusic();

        } else if (MusicService.Action.STOP_PLAYING_MUSIC.equals(action)) {
            mOnMusicControlListener.onStopPlayingMusic();
        }
    }
}
