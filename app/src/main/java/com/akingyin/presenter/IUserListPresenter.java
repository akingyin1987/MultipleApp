package com.akingyin.presenter;

import android.os.Bundle;
import android.view.View;

import com.md.multipleapp.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public interface IUserListPresenter {

    public   void   initialize(Bundle savedInstanceState,View view);//



    public   void    addUser(UserEntity  userEntity);

    public   void    modifyUser(UserEntity  userEntity);

    public   void    delectUser(UserEntity  userEntity);

    public List<UserEntity> findUsers();

    public   void     backActivity();
}
