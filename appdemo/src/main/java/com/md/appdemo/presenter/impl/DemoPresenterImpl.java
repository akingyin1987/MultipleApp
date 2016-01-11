package com.md.appdemo.presenter.impl;

import com.md.appdemo.model.AAfindUser;
import com.md.appdemo.model.UserEntity;
import com.md.appdemo.model.impl.AAfindUserImpl;
import com.md.appdemo.presenter.DemoPresenter;
import com.md.appdemo.ui.IdemoView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by zlcd on 2016/1/11.
 */

@EBean
public class DemoPresenterImpl  implements DemoPresenter {

    @Bean(AAfindUserImpl.class)
    public AAfindUser   finduser;

    public IdemoView   iview;

    public   DemoPresenterImpl(){}

    public IdemoView getIview() {
        return iview;
    }

    public void setIview(IdemoView iview) {
        this.iview = iview;
    }

    @Override
    public void init() {
        iview.initView();

    }

    @Override
    public void addUser(UserEntity userEntity) {
          boolean   add = finduser.inspectUser(userEntity);
        if(add){
            iview.showMessage("新增成功");
            iview.addUser(userEntity);
        }else{
            iview.showMessage("新增失败");
        }
    }

    @Override
    public void delectUser(UserEntity userEntity) {

    }

    @Override
    public void modifyUser(UserEntity userEntity) {

    }

    @Override
    public void findAllUser() {
        List<UserEntity>   items =finduser.findAllUser();
        iview.findAllUser(items);
        iview.showMessage("查询总数："+items.size());
    }
}
