package com.dl.dlexerciseandroid.ui.exoplayer.player;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class PlayerActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_player);
        initialize();
    }

    private void initialize() {

    }
}
