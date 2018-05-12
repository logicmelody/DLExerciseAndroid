package com.dl.dlexerciseandroid.data.model;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class Task {

    public long id;
    public String title;
    public String description;
    public String laterPackageName;
    public String laterCallback;
    public long time = 0L;


    public Task(long id, String title, String description, String laterPackageName, String laterCallback, long time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.laterPackageName = laterPackageName;
        this.laterCallback = laterCallback;
        this.time = time;
    }
}
