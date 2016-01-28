package com.akingyin.model.impl;

import android.support.annotation.NonNull;

import com.activeandroid.query.Select;
import com.akingyin.model.IUserModel;
import com.md.multipleapp.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserModelImpl  implements IUserModel {
    @Override
    public boolean addUser(@NonNull UserEntity userEntity) {
        try {
            userEntity.save();
            return  true;
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean modeifyUser(@NonNull UserEntity userEntity) {
        try {
            userEntity.save();
            return  true;
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean delectUser(@NonNull UserEntity userEntity) {
        try {
            userEntity.delete();
            return  true;
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public List<UserEntity> findUsers() {
        return new Select().from(UserEntity.class).execute();
    }
}
