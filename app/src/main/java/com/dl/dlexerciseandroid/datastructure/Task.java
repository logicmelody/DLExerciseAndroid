package com.dl.dlexerciseandroid.datastructure;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class Task {

    public String title;
    public String description;
    public long time = 0L;


    public Task(String title, String description, long time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }
}
