package com.akingyin.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2016/1/28.
 */
public interface IUserListView {

    public   void   initialize(Bundle savedInstanceState,View view);//

    public   void   showMessage(String  message);

    public   void   showMessage(View v,String message);
}
