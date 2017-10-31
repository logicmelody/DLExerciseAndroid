package com.dl.dlexerciseandroid.ui.installedapps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.installedapps.InstalledApp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/10/31.
 */

public class InstalledAppsAdapter extends RecyclerView.Adapter<InstalledAppsAdapter.InstalledAppViewHolder> {

    private Context mContext;

    private List<InstalledApp> mDataList;


    public class InstalledAppViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAppIcon;
        private TextView mAppName;


        public InstalledAppViewHolder(View itemView) {
            super(itemView);
            findViews();
        }

        private void findViews() {
            mAppIcon = (ImageView) itemView.findViewById(R.id.image_view_installed_app_item_icon);
            mAppName = (TextView) itemView.findViewById(R.id.text_view_installed_app_item_name);
        }

        public void bind(InstalledApp installedApp) {
            mAppIcon.setImageDrawable(installedApp.getIcon());
            mAppName.setText(installedApp.getName());
        }
    }

    public InstalledAppsAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public InstalledAppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InstalledAppViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_installed_app, parent, false));
    }

    @Override
    public void onBindViewHolder(InstalledAppViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(InstalledApp installedApp) {
        mDataList.add(installedApp);
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
