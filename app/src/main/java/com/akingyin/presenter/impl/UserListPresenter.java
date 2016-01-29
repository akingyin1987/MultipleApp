package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.akingyin.model.IUserModel;
import com.akingyin.model.impl.UserModelImpl;
import com.akingyin.presenter.IUserListPresenter;
import com.akingyin.view.IUserListView;
import com.md.multipleapp.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserListPresenter implements IUserListPresenter {

    private IUserModel   userModel;

    private IUserListView   userListView;

    public UserListPresenter(IUserListView userListView) {
        this.userListView = userListView;
        userModel = new UserModelImpl();
    }

    @Override
    public void initialize(Bundle savedInstanceState,View view) {
        userListView.initialize(savedInstanceState,view);
    }

    @Override
    public void addUser(String userName, String agestr) {
        System.out.println(userName+":"+agestr);
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(agestr)){
            userListView.showMessage("用户名，年龄不能为空");
            return;
        }
        UserEntity   userEntity = new UserEntity();
        try{
            userEntity.userName = userName;
            userEntity.age = Integer.parseInt(agestr);
            userEntity = userModel.addUser(userEntity);
            if(null == userEntity){
                userListView.showMessage("数据保存失败");
            }else{
                userListView.addUserSucess(userEntity);
            }
        }catch ( Exception e){
            e.printStackTrace();
            userListView.showMessage("数据出错了"+e.getMessage());
        }
    }

    @Override
    public void modifyUser(UserEntity userEntity) {

    }

    @Override
    public void delectUser(UserEntity userEntity) {

    }

    @Override
    public List<UserEntity> findUsers() {
        return  userModel.findUsers();
    }

    @Override
    public void backActivity() {

    }
}
