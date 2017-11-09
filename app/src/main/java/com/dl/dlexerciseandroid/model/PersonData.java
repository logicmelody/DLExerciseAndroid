package com.dl.dlexerciseandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/9.
 */

public class PersonData {

    @SerializedName("age")
    @Expose
    private int age;

    @SerializedName("weight")
    @Expose
    private int weight;

    @SerializedName("height")
    @Expose
    private int height;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "age='" + age + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
