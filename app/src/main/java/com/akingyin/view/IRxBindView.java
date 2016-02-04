package com.akingyin.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by zlcd on 2016/2/4.
 */
public interface IRxBindView {

    public   void   initialize(Bundle savedInstanceState,View view);//



    public   void   showMessage(String  message);

    public   void   showMessage(View v,String message);

    public   void   printMessage(String  message);
}
