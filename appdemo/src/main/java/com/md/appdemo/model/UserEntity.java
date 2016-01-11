package com.md.appdemo.model;



/**
 * Created by zlcd on 2016/1/11.
 */


public class UserEntity {


    public   String   userName;

    public   Long     Id;


    public   int    age;


    @Override
    public String toString() {
        return "UserEntity{" +
            "userName='" + userName + '\'' +
            ", age=" + age +
            '}';
    }
}
