package com.dl.dlexerciseandroid.ui.installedapps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.installedapps.InstalledApp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        loadInstalledApps();
    }

    private void findViews() {
        mInstalledAppsRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_installed_apps_installed_apps);
    }

    private void setupInstalledAppsRecyclerView() {
        mInstalledAppsAdapter = new InstalledAppsAdapter(mContext);

        mInstalledAppsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mInstalledAppsRecyclerView.setAdapter(mInstalledAppsAdapter);
    }

    private void loadInstalledApps() {
        Observable.create(new ObservableOnSubscribe<InstalledApp>() {
            @Override
            public void subscribe(ObservableEmitter<InstalledApp> subscriber) throws Exception {
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

                List<ResolveInfo> pkgAppsList
                        = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);

                for (ResolveInfo resolveInfo : pkgAppsList) {
                    // 直接存Drawable到Adapter的ArrayList中可能會有memory的問題
                    subscriber.onNext(new InstalledApp(resolveInfo.loadIcon(getActivity().getPackageManager()),
                            resolveInfo.loadLabel(getActivity().getPackageManager()).toString()));
                }

                // 記得要call onNext()跟onComplete()！不然在Observer的callback會接收不到
                subscriber.onComplete();
            }
        })
                .map(new Function<InstalledApp, InstalledApp>() {
                    @Override
                    public InstalledApp apply(InstalledApp installedApp) throws Exception {
                        installedApp.setName(installedApp.getName().toLowerCase());

                        return installedApp;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InstalledApp>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InstalledApp installedApp) {
                        mInstalledAppsAdapter.add(installedApp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "Error in loading installed apps", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        mInstalledAppsAdapter.refresh();
                        Toast.makeText(mContext, "Load installed apps completed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
