package com.md.multipleapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by zlcd on 2016/1/11.
 */

@Table(name = "tb_user")
public class UserEntity extends Model {

    @Column(name = "userName")
    public   String   userName;

    @Column(name = "age")
    public   int    age;


    @Override
    public String toString() {
        return "UserEntity{" +
            "userName='" + userName + '\'' +
            ", age=" + age +
            '}';
    }
}
