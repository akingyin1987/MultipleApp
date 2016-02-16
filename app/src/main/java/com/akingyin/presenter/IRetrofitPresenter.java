package com.akingyin.presenter;

import android.os.Bundle;
import android.view.View;



/**
 * Created by zlcd on 2016/2/16.
 */
public interface IRetrofitPresenter {
    public   void   initialize(Bundle savedInstanceState,View view);//

    public   void   findUserInfo(String  account);


    public   void   findBodyUserInfo(String  account);



}
