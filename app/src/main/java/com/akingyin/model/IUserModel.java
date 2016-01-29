package com.akingyin.model;

import android.support.annotation.NonNull;

import com.md.multipleapp.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public interface IUserModel {

    public   UserEntity    addUser(@NonNull UserEntity  userEntity);

    public   UserEntity    modeifyUser(@NonNull UserEntity  userEntity);

    public   UserEntity    delectUser(@NonNull UserEntity  userEntity);

    public List<UserEntity>   findUsers();
}
