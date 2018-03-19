package com.dl.dlexerciseandroid.ui.exoplayer.player;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dl.dlexerciseandroid.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class PlayerActivity extends AppCompatActivity implements PlayerContract.View {

    private PlayerContract.Presenter mPresenter;

    private SimpleExoPlayer mPlayer;

    private boolean mPlayWhenReady = true;

    // Initialize with 0 to start with the first item in the Timeline
	// 在 play list (Timeline) 中，media 的位置
    private int mCurrentWindow = 0;

    // Initialize with 0 to start from the beginning of the window
	// 此則 media 現在播放到什麼位置
    private int mPlaybackPosition = 0;


    @BindView(R.id.simple_exo_player_view_exo_player)
    public SimpleExoPlayerView mSimpleExoPlayerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_player);
	    ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        mPresenter = new PlayerPresenter(this);

        setupPlayer();
        prepareMedia();
    }

	private void setupPlayer() {
    	// Roughly a RenderersFactory creates renderers for timestamp synchronized rendering of video, audio and text (subtitles).
		// The TrackSelector is responsible for selecting from the available audio, video and text tracks
		// The LoadControl manages buffering of the player.
		mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());

		mPlayer.setPlayWhenReady(mPlayWhenReady);
		mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

		mSimpleExoPlayerView.setPlayer(mPlayer);
	}

	private void prepareMedia() {
		Uri uri = Uri.parse("This is media url");
		MediaSource mediaSource = buildMediaSource(uri);

		mPlayer.prepare(mediaSource, true, false);
	}

	private MediaSource buildMediaSource(Uri uri) {
		return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(getString(R.string.app_name))).createMediaSource(uri);
	}

	@Override
    public void setPresenter(PlayerContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
