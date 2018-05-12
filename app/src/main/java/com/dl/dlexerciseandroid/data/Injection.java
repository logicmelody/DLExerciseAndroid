package com.dl.dlexerciseandroid.data;

import com.dl.dlexerciseandroid.data.local.LocalDataSource;
import com.dl.dlexerciseandroid.data.remote.RemoteDataSource;

public class Injection {

    public static Repository provideRepository() {
        return Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance());
    }
}
