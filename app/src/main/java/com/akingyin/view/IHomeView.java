package com.akingyin.view;

import android.view.View;

/**
 * Created by Administrator on 2016/1/26.
 */
public interface IHomeView {

    public   void   initialize();//

    public   void   showMessage(String  message);

    public   void   showMessage(View v,String message);

    public   void   OpenMultipleApp();
}
