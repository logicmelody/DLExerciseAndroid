package com.dl.dlexerciseandroid.musicplayer.musiccontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by logicmelody on 2016/5/11.
 */
public class MusicControlReceiver extends BroadcastReceiver {

    public static final class Action {
        public static final String CHANGE_MUSIC = "com.dl.dlexerciseandroid.ACTION_CHANGE_MUSIC";
    }

    public interface OnMusicControlListener {
        void onChangeMusic();
    }

    private OnMusicControlListener mOnMusicControlListener;


    public MusicControlReceiver(OnMusicControlListener listener) {
        mOnMusicControlListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Action.CHANGE_MUSIC.equals(action)) {
            mOnMusicControlListener.onChangeMusic();
        }
    }
}
