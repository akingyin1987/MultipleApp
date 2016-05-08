package com.akingyin.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2016/1/26.
 */
public interface IHomeView {

    public   void   initialize(Bundle savedInstanceState);//

    public   void   showMessage(String  message);

    public   void   showMessage(View v,String message);

    public   void   OpenMultipleApp();

    public   void   switchUserManager();

    public   void   switchFlashlight();

    public   void   switchVoiceRecord();

    public   void   switchRxview();

    public   void   switchRetrofit();

    public   void   switchChangeLog();
}
