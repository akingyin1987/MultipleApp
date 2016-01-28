package com.akingyin.model;

import android.support.annotation.NonNull;

import com.md.multipleapp.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public interface IUserModel {

    public   boolean    addUser(@NonNull UserEntity  userEntity);

    public   boolean    modeifyUser(@NonNull UserEntity  userEntity);

    public   boolean    delectUser(@NonNull UserEntity  userEntity);

    public List<UserEntity>   findUsers();
}
