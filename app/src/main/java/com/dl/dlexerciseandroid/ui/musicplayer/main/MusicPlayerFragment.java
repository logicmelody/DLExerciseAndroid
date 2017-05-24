package com.dl.dlexerciseandroid.ui.musicplayer.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.backgroundtask.service.MusicService;
import com.dl.dlexerciseandroid.datastructure.Music;
import com.dl.dlexerciseandroid.ui.musicplayer.musiccontroller.MusicControlReceiver;
import com.dl.dlexerciseandroid.ui.musicplayer.musiccontroller.MusicControllerActivity;
import com.dl.dlexerciseandroid.utility.utils.MusicUtils;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicPlayerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, MusicControlReceiver.OnMusicControlListener {

    public static final String TAG = MusicPlayerFragment.class.getName();
    public static final Uri sAudioContentUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private static final int LOADER_ID = 47;
    private static final int READ_CODE_EXTERNAL_STORAGE = 1;

    private Context mContext;

    private MusicService mMusicService;

    private RecyclerView mMusicList;
    private MusicListAdapter mMusicListAdapter;

    private TextView mNoMusicText;

    private ViewGroup mPlayingMusicContainer;
    private TextView mPlayingMusicTitle;

    private ViewGroup mRequestPermissionExplanation;
    private Button mRequestPermissionButton;

    private MusicControlReceiver mMusicControlReceiver;

    private boolean mIsMusicServiceBound = false;

    // MusicPlayerFragment需要顯示現在正在播放的音樂在最下方，所以也需要與MusicService bind
    private ServiceConnection mMusicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("danny", "Bind MusicService from MusicPlayerFragment");

            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;

            // Get service
            mMusicService = binder.getService();
            mIsMusicServiceBound = true;

            if (mMusicService.getPlayingMusic() != null) {
                mPlayingMusicTitle.setText(mMusicService.getPlayingMusic().title);
                mPlayingMusicContainer.setVisibility(View.VISIBLE);

            } else {
                mPlayingMusicContainer.setVisibility(View.GONE);
            }
        }

        // 這個method在service crash或是service被系統kill掉的時候會被呼叫，如果是client unbind則不會呼叫此method
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsMusicServiceBound = false;
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_player, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();

        if (hasAccessMusicFilesPermission()) {
            if (savedInstanceState == null) {
                // 正常打開此Fragment
                getLoaderManager().initLoader(LOADER_ID, null, this);

            } else {
                // 此Fragment經過旋轉後重新create
                getLoaderManager().restartLoader(LOADER_ID, null, this);
            }

        } else {
            showRequestPermissionOrExplanationDialog();
        }
    }

    private void initialize() {
        mMusicControlReceiver = new MusicControlReceiver(this);

        findViews();
        setupViews();
        setupMusicList();
    }

    private void findViews() {
        mMusicList = (RecyclerView) getView().findViewById(R.id.recycler_view_music_player_list);
        mNoMusicText = (TextView) getView().findViewById(R.id.text_view_music_player_no_music);
        mPlayingMusicContainer = (ViewGroup) getView().findViewById(R.id.view_group_music_player_playing_item);
        mPlayingMusicTitle = (TextView) getView().findViewById(R.id.text_view_music_player_playing_title);
        mRequestPermissionExplanation
                = (ViewGroup) getView().findViewById(R.id.view_group_music_player_request_permission_explanation);
        mRequestPermissionButton = (Button) getView().findViewById(R.id.button_music_player_request_permission);
    }

    private void setupViews() {
        mPlayingMusicContainer.setOnClickListener(this);
        mRequestPermissionButton.setOnClickListener(this);
    }

    private void setupMusicList() {
        mMusicListAdapter = new MusicListAdapter(mContext);

        mMusicList.setLayoutManager(new LinearLayoutManager(mContext));
        mMusicList.setAdapter(mMusicListAdapter);
    }

    private boolean hasAccessMusicFilesPermission() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void showRequestPermissionOrExplanationDialog() {
        // Should we show an explanation?
        // 在Fragment中如果想要shouldShowRequestPermissionRationale，根據最新的support-v4 library，
        // 直接用Fragment class中的method即可
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // 如果user有在permission跳出的時候，選擇deny，或是自己手動到Settings中把permission關掉，就會走到這塊，
            // 也就是需要跳出explanation

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            Log.d("danny", "Show READ_EXTERNAL_STORAGE explanation");

            mRequestPermissionExplanation.setVisibility(View.VISIBLE);

        } else {
            // 第一次跳出permission視窗的時候，不會跳出explanation，會直接向user提出request permission
            // No explanation needed, we can request the permission.
            Log.d("danny", "No explanation needed, we can request the permission.");
            requestAccessMusicFilesPermission();
        }
    }

    private void requestAccessMusicFilesPermission() {
        // 在Fragment中如果想要requestPermissions，根據最新的support-v4 library，直接用Fragment class中的method即可
        requestPermissions(
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_CODE_EXTERNAL_STORAGE);

        // READ_CODE_EXTERNAL_STORAGE is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(MusicService.Action.STOP_PLAYING_MUSIC);

        mContext.registerReceiver(mMusicControlReceiver, intentFilter);
        mContext.bindService(new Intent(mContext, MusicService.class), mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        mContext.unregisterReceiver(mMusicControlReceiver);
        unbindMusicService();
    }

    @Override
    public void onDestroy() {
        //Log.d("danny", "Destroy MusicService from MusicPlayerFragment");
        //mContext.stopService(new Intent(mContext, MusicService.class));

        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // 擷取在device中音樂檔案的資訊，一樣可以用LoaderManager來處理，只是Uri的部分要用
        // android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        String order = android.provider.MediaStore.Audio.Media.TITLE;

        return new CursorLoader(mContext, sAudioContentUri, null, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }

        setMusicListData(cursor);
    }

    private void setMusicListData(Cursor cursor) {
        mMusicListAdapter.clear();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int titleIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int artistIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);

            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            String artist = cursor.getString(artistIndex);
            String musicFilePath = MusicUtils.getAudioFilePath(mContext,
                    ContentUris.withAppendedId(sAudioContentUri, id));

            // 判斷music是不是mp3檔
            if (MusicUtils.isMusicFile(musicFilePath)) {
                mMusicListAdapter.add(new Music(id, title, artist));
            }
        }

        if (mMusicListAdapter.getDataListSize() == 0) {
            mNoMusicText.setVisibility(View.VISIBLE);
            mMusicList.setVisibility(View.GONE);

        } else {
            mNoMusicText.setVisibility(View.GONE);
            mMusicList.setVisibility(View.VISIBLE);
        }

        mMusicListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_group_music_player_playing_item:
                Intent intent = new Intent(mContext, MusicControllerActivity.class);
                intent.putExtra(MusicControllerActivity.EXTRA_PLAYING_MUSIC, mMusicService.getPlayingMusic());

                mContext.startActivity(intent);

                break;

            case R.id.button_music_player_request_permission:
                requestAccessMusicFilesPermission();

                break;
        }
    }

    @Override
    public void onChangeMusic() {

    }

    // 如果是從notification關掉MusicService，會呼叫這個callback
    // 我們這邊做的事情是把service unbind，並且將playing music item hide起來
    @Override
    public void onStopPlayingMusic() {
        unbindMusicService();
        mPlayingMusicContainer.setVisibility(View.GONE);
    }

    // 因為unbind service可能會走onStopPlayingMusic()這條路，而不是一般正常的onStop()，
    // 如果在onStopPlayingMusic()已經unbind，而在onStop()又unbind一次，app會crash
    // 所以我們寫一個method：在unbind之前會先檢查component有沒有跟service bind在一起
    private void unbindMusicService() {
        if (!mIsMusicServiceBound) {
            return;
        }

        Log.d("danny", "Unbind MusicService from MusicPlayerFragment");

        mIsMusicServiceBound = false;
        mContext.unbindService(mMusicConnection);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case READ_CODE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("danny", "READ_EXTERNAL_STORAGE permission was granted!");

                    mRequestPermissionExplanation.setVisibility(View.GONE);
                    getLoaderManager().initLoader(LOADER_ID, null, this);

                } else {
                    Log.d("danny", "READ_EXTERNAL_STORAGE permission was denied!");

                    mRequestPermissionExplanation.setVisibility(View.VISIBLE);

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}