package com.dl.dlexerciseandroid.musicplayer.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.background.service.MusicService;
import com.dl.dlexerciseandroid.datastructure.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicPlayerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "com.dl.dlexerciseandroid.MusicPlayerFragment";

    private static final int LOADER_ID = 47;

    private Context mContext;

    private RecyclerView mMusicList;
    private MusicListAdapter mMusicListAdapter;

    private List<Music> mMusicDataList = new ArrayList<>();


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
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void initialize() {
        findViews();
        setupMusicList();
    }

    private void findViews() {
        mMusicList = (RecyclerView) getView().findViewById(R.id.recycler_view_music_player_list);
    }

    private void setupMusicList() {
        mMusicListAdapter = new MusicListAdapter(mContext, mMusicDataList);

        mMusicList.setLayoutManager(new LinearLayoutManager(mContext));
        mMusicList.setAdapter(mMusicListAdapter);
    }

    @Override
    public void onDestroy() {
        Log.d("danny", "Stop MusicService from MusicPlayerFragment");
        mContext.stopService(new Intent(mContext, MusicService.class));

        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // 擷取在device中音樂檔案的資訊，一樣可以用LoaderManager來處理，只是Uri的部分要用
        // android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String order = android.provider.MediaStore.Audio.Media.TITLE;

        return new CursorLoader(mContext, uri, null, null, null, order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }

        setMusicListData(cursor);
    }

    private void setMusicListData(Cursor cursor) {
        mMusicDataList.clear();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int titleIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int artistIndex = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);

            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            String artist = cursor.getString(artistIndex);

            mMusicDataList.add(new Music(id, title, artist));
        }

        mMusicListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
