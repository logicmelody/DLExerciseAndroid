package com.dl.dlexerciseandroid.features.spring;

/**
 * Created by logicmelody on 2016/4/1.
 */

// 我們需要一個representation class來幫助我們model從web service傳來的JSON data
public class Greeting {

    private String id;
    private String content;


    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }
}
