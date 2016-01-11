package com.md.appdemo.presenter;

import com.md.appdemo.model.UserEntity;

/**
 * Created by zlcd on 2016/1/11.
 */
public interface DemoPresenter {

       void    init();

       void    addUser(UserEntity userEntity);

       void    delectUser(UserEntity userEntity);

       void    modifyUser(UserEntity userEntity);

       void    findAllUser();



}
