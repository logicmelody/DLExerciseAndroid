package com.dl.dlexerciseandroid.ui.installedapps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.installedapps.InstalledApp;

/**
 * Created by logicmelody on 2017/10/31.
 */

public class InstalledAppsFragment extends Fragment {

    public static final String TAG = InstalledAppsFragment.class.getName();

    private Context mContext;

    private RecyclerView mInstalledAppsRecyclerView;
    private InstalledAppsAdapter mInstalledAppsAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installed_apps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupInstalledAppsRecyclerView();

        mInstalledAppsAdapter.add(new InstalledApp("", "App1"));
        mInstalledAppsAdapter.add(new InstalledApp("", "App2"));
        mInstalledAppsAdapter.add(new InstalledApp("", "App3"));
    }

    private void findViews() {
        mInstalledAppsRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_installed_apps_installed_apps);
    }

    private void setupInstalledAppsRecyclerView() {
        mInstalledAppsAdapter = new InstalledAppsAdapter(mContext);

        mInstalledAppsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mInstalledAppsRecyclerView.setAdapter(mInstalledAppsAdapter);
    }
}
