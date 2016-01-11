package com.md.appdemo.model;

import java.util.List;

/**
 * Created by zlcd on 2016/1/11.
 */
public interface AAfindUser {

    boolean  inspectUser(UserEntity userEntity);

    boolean  uploadUser(UserEntity userEntity);

    boolean  delectUser(UserEntity userEntity);

    UserEntity  findUserById(long id);

    List<UserEntity>   findAllUser();
}
