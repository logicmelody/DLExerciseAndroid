package com.dl.dlexerciseandroid.data.remote;

import com.dl.dlexerciseandroid.data.DataSource;

public class RemoteDataSource implements DataSource {

    private static volatile RemoteDataSource sRemoteDataSource = null;


    public static RemoteDataSource getInstance() {
        if (sRemoteDataSource == null) {
            synchronized (RemoteDataSource.class) {
                if (sRemoteDataSource == null) {
                    sRemoteDataSource = new RemoteDataSource();
                }
            }
        }

        return sRemoteDataSource;
    }

    private RemoteDataSource() {

    }

    @Override
    public void getMovies() {
        // 跟API要資料的方法實作
    }
}
