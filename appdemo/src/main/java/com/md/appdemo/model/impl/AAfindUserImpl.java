package com.md.appdemo.model.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.md.appdemo.model.AAfindUser;
import com.md.appdemo.model.UserEntity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zlcd on 2016/1/11.
 */

@EBean
public class AAfindUserImpl  implements AAfindUser {

    public   static Uri contentUri = Uri.parse("content://org.jraf.androidcontentprovidergenerator.sample.provider/tb_user");

    @RootContext
    Context   context;


    ContentResolver contentResolver;

    @Override
    public boolean inspectUser(UserEntity userEntity) {
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }
        ContentValues  contentValues = new ContentValues();
        contentValues.put("userName",userEntity.userName);
        contentValues.put("age",userEntity.age);

        Uri uri =contentResolver.insert(contentUri, contentValues);
        String id= uri.getLastPathSegment();
        return false;
    }

    @Override
    public boolean uploadUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public boolean delectUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public UserEntity findUserById(long id) {
        return null;
    }

    @Override
    public List<UserEntity> findAllUser() {
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }

        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);
        List<UserEntity>   data = new ArrayList<>();
        if(null == cursor){
            return  data;
        }
        while (cursor.moveToNext()){
            UserEntity  userEntity = new UserEntity();
            userEntity.age = cursor.getInt(cursor.getColumnIndex("age"));
            userEntity.userName = cursor.getString(cursor.getColumnIndex("userName"));
            userEntity.Id = cursor.getLong(cursor.getColumnIndex("Id"));
            data.add(userEntity);
        }

            cursor.close();

        return data;
    }
}
