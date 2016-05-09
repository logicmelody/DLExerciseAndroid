package com.dl.dlexerciseandroid.musicplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Music;

import java.util.List;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class MusicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class MusicViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView artist;

        public MusicViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_view_music_item_title);
            artist = (TextView) itemView.findViewById(R.id.text_view_music_item_artist);
        }
    }

    private Context mContext;

    private List<Music> mDataList;


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
}
