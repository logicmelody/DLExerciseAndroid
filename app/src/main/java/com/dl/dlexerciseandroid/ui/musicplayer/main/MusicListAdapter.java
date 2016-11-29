package com.dl.dlexerciseandroid.ui.musicplayer.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.backgroundtask.service.MusicService;
import com.dl.dlexerciseandroid.datastructure.Music;
import com.dl.dlexerciseandroid.ui.musicplayer.musiccontroller.MusicControllerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView artist;

        public MusicViewHolder(View itemView) {
            super(itemView);
            findViews();
            setupViews();
        }

        private void findViews() {
            title = (TextView) itemView.findViewById(R.id.text_view_music_item_title);
            artist = (TextView) itemView.findViewById(R.id.text_view_music_item_artist);
        }

        private void setupViews() {
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Log.d("danny", "Start MusicService from MusicListAdapter");

                // 在每次user choose一首歌的時候，我們就把最新的music list和chosen music的position透過intent傳給Service
                // Note: startService()
                mContext.startService(MusicService.generateStartPlayingMusicIntent(mContext,
                                      (ArrayList<Music>) mDataList, getAdapterPosition()));

                // 我們把user choose的music資訊也傳給MusicControllerActivity，
                // 這樣在UI顯示上就不需要等到與service bind成功才show出一些歌名, 作者等基本資訊
                Intent intent = new Intent(mContext, MusicControllerActivity.class);
                intent.putExtra(MusicControllerActivity.EXTRA_PLAYING_MUSIC, mDataList.get(getAdapterPosition()));

                mContext.startActivity(intent);
            }
        }
    }

    private Context mContext;

    private List<Music> mDataList;


    public MusicListAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public MusicListAdapter(Context context, List<Music> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_music_player_music, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicViewHolder vh = (MusicViewHolder) holder;

        vh.title.setText(mDataList.get(position).title);
        vh.artist.setText(mDataList.get(position).artist);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clear() {
        mDataList.clear();
    }

    public void add(Music music) {
        mDataList.add(music);
    }

    public int getDataListSize() {
        return mDataList.size();
    }
}
