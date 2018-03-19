package com.dl.dlexerciseandroid.ui.exoplayer.player;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class PlayerActivity extends AppCompatActivity implements PlayerContract.View {

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private PlayerContract.Presenter mPresenter;

    private SimpleExoPlayer mPlayer;

    private boolean mPlayWhenReady = true;

    // Initialize with 0 to start with the first item in the Timeline
	// 在 play list (Timeline) 中，media 的位置
    private int mCurrentWindow = 0;

    // Initialize with 0 to start from the beginning of the window
	// 此則 media 現在播放到什麼位置
    private long mPlaybackPosition = 0;

    private ComponentListener mComponentListener;

    @BindView(R.id.simple_exo_player_view_exo_player)
    public SimpleExoPlayerView mSimpleExoPlayerView;


    private class ComponentListener extends Player.DefaultEventListener {

        // boolean playWhenReady: 負責管 play / pause media
        // true: playing
        // false: paused
        // This corresponds with the method ExoPlayer.setPlayWhenReady(boolean playWhenReady)
        // 所以之前我們有在 onPlayerStateChanged() 裡面 call 過 ExoPlayer.setPlayWhenReady()，導致無窮迴圈
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;

            switch (playbackState) {
                // The player has been instantiated but has not being prepared with a MediaSource yet..
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;

                // The player is not able to immediately play from the current position because not enough data is buffered.
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;

                // The player is able to immediately play from the current position.
                // This means the player does actually play media when playWhenReady is true.
                // If it is false the player is paused.
                case Player.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;

                // The player has finished playing the media.
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;

                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }

            Log.d("danny", "changed state to " + stateString + " playWhenReady: " + playWhenReady);

            if (playWhenReady && playbackState == Player.STATE_READY) {
                // actually playing media
                Log.d("danny", "Actually playing media");
            }
        }
    }

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		ButterKnife.bind(this);
		initialize();
	}

	private void initialize() {
        mPresenter = new PlayerPresenter(this);
        mComponentListener = new ComponentListener();
    }

	@Override
	protected void onStart() {
		super.onStart();
		setupPlayer();
		prepareMedia(getString(R.string.exoplayer_url_dash));
	}

	private void setupPlayer() {
		hideSystemUi();

		if (mPlayer == null) {
            // A factory to create an AdaptiveVideoTrackSelection
            // 搭配 DefaultBandwidthMeter，可以根據現在的網路狀況和頻寬，來選擇適合的 track 來播放
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            // Roughly a RenderersFactory creates renderers for timestamp synchronized rendering of video, audio and text (subtitles).
            // The TrackSelector is responsible for selecting from the available audio, video and text tracks
            // The LoadControl manages buffering of the player.
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());

            mPlayer.setPlayWhenReady(mPlayWhenReady);
            mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
            mPlayer.addListener(mComponentListener);

            mSimpleExoPlayerView.setPlayer(mPlayer);
        }
	}

	/**
	 * Have a pure full screen experience
	 */
	@SuppressLint("InlinedApi")
	private void hideSystemUi() {
		mSimpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	@Override
	protected void onStop() {
		super.onStop();
		releasePlayer();
	}

	/**
	 * 因為我們的 player 在 onStop() 的時候就會被 release 掉，但是 app 可能會被重新開啟，所以我們需要記下現在 media 播到哪的時間
	 * For some of these codecs it may have only one instance so not releasing it would harm other apps.
	 */
	private void releasePlayer() {
		if (mPlayer != null) {
			mPlaybackPosition = mPlayer.getCurrentPosition();
			mCurrentWindow = mPlayer.getCurrentWindowIndex();
			mPlayWhenReady = mPlayer.getPlayWhenReady();

            mPlayer.removeListener(mComponentListener);
			mPlayer.release();
			mPlayer = null;
		}
	}

	private void prepareMedia(String url) {
		Uri uri = Uri.parse(url);

//		MediaSource mediaSource = buildExtractorMediaSource(uri);
        MediaSource mediaSource = buildDashMediaSource(uri);

		mPlayer.prepare(mediaSource, true, false);
	}

	private MediaSource buildExtractorMediaSource(Uri uri) {
		return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(getString(R.string.app_name))).createMediaSource(uri);
	}

    /**
     * DASH is a widely used adaptive streaming format.
     *
     * @param uri
     * @return
     */
    private MediaSource buildDashMediaSource(Uri uri) {
        DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(getString(R.string.app_name));
        DashChunkSource.Factory dashChunkSourceFactory =
                new DefaultDashChunkSource.Factory(new DefaultHttpDataSourceFactory(getString(R.string.app_name), BANDWIDTH_METER));

        return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri);
    }

	@Override
    public void setPresenter(PlayerContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
