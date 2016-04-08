package com.md.appdemo.model.impl;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Contacts;

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
        if(null == uri){
            return  false;
        }
        long  rawContactId = ContentUris.parseId(uri);
        userEntity.Id = rawContactId;
        return  true;

    }

    @Override
    public boolean uploadUser(UserEntity userEntity) {
        if(null == userEntity || null == userEntity.Id){
            return  false;
        }
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }
        ContentValues   contentValues = new ContentValues();
        contentValues.put("userName",userEntity.userName);
        contentValues.put("age", userEntity.age);
        int result =contentResolver.update(contentUri,contentValues,BaseColumns._ID+"=?",new String[]{String.valueOf(userEntity.Id)});
        return result>0;
    }

    @Override
    public boolean delectUser(UserEntity userEntity) {
        if(null == userEntity || null == userEntity.Id){
            return  false;
        }
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }
        int result = contentResolver.delete(contentUri, BaseColumns._ID+"=?", new String[]{String.valueOf(userEntity.Id)});
        return result>0;
    }

    @Override
    public UserEntity findUserById(long id) {
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }
        Uri  uri = ContentUris.withAppendedId(contentUri,id);
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        UserEntity  userEntity = new UserEntity();
        if(null != cursor && cursor.moveToNext()){

            userEntity.age = cursor.getInt(cursor.getColumnIndex("age"));
            userEntity.userName = cursor.getString(cursor.getColumnIndex("userName"));
            userEntity.Id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            cursor.close();
            return  userEntity;
        }
        return null;
    }

    @Override
    public List<UserEntity> findAllUser() {
        if(null == contentResolver ){
            contentResolver = context.getContentResolver();
        }

        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);
        List<UserEntity>   data = new ArrayList<UserEntity>();
        if(null == cursor){
            return  data;
        }
        while (cursor.moveToNext()){
            UserEntity  userEntity = new UserEntity();
            userEntity.age = cursor.getInt(cursor.getColumnIndex("age"));
            userEntity.userName = cursor.getString(cursor.getColumnIndex("userName"));
            userEntity.Id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            data.add(userEntity);
        }

            cursor.close();

        return data;
    }
}
