package com.md.appdemo.ui;

import android.view.View;

import com.md.appdemo.model.UserEntity;

import java.util.List;

/**
 * Created by zlcd on 2016/1/11.
 */
public interface IdemoView {

    void   initView();

    void   showMessage(String message);

    void   showMessage(String message, View v);

    void   addUser(UserEntity userEntity);

    void   modifyUser(UserEntity userEntity);

    void   delectUser(UserEntity userEntity);

    void   findAllUser(List<UserEntity> userEntities);
}
