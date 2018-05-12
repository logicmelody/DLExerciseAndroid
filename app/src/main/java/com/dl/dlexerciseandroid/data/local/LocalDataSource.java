package com.dl.dlexerciseandroid.data.local;

import com.dl.dlexerciseandroid.data.DataSource;

public class LocalDataSource implements DataSource {

    private static volatile LocalDataSource sLocalDataSource = null;


    public static LocalDataSource getInstance() {
        if (sLocalDataSource == null) {
            synchronized (LocalDataSource.class) {
                if (sLocalDataSource == null) {
                    sLocalDataSource = new LocalDataSource();
                }
            }
        }

        return sLocalDataSource;
    }

    private LocalDataSource() {

    }

    @Override
    public void getMovies() {
        // 跟local端要資料的方法實作
    }
}
