package com.dl.dlexerciseandroid.data;

import android.support.annotation.NonNull;

import com.dl.dlexerciseandroid.data.local.LocalDataSource;
import com.dl.dlexerciseandroid.data.remote.RemoteDataSource;

public class Repository implements DataSource {

    private static volatile Repository sRepository = null;

    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;


    public static Repository getInstance(@NonNull RemoteDataSource remoteDataSource,
                                         @NonNull LocalDataSource localDataSource) {
        if (sRepository == null) {
            synchronized (Repository.class) {
                if (sRepository == null) {
                    sRepository = new Repository(remoteDataSource, localDataSource);
                }
            }
        }

        return sRepository;
    }

    private Repository(@NonNull RemoteDataSource remoteDataSource, @NonNull LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public void getMovies() {
        mRemoteDataSource.getMovies();
        mLocalDataSource.getMovies();
    }

    public void release() {
        sRepository = null;
    }
}
