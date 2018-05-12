package com.dl.dlexerciseandroid;

import com.dl.dlexerciseandroid.data.Repository;
import com.dl.dlexerciseandroid.data.local.LocalDataSource;
import com.dl.dlexerciseandroid.data.remote.RemoteDataSource;
import com.dl.dlexerciseandroid.widget.schedulers.BaseSchedulerProvider;
import com.dl.dlexerciseandroid.widget.schedulers.SchedulerProvider;

public class Injection {

    public static Repository provideRepository() {
        return Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
