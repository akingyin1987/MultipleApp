package com.akingyin.view;

import android.os.Bundle;
import android.view.View;

/**
 * Created by zlcd on 2016/1/29.
 */
public interface IFlashlightView {

    public   void   initialize(Bundle savedInstanceState,View view);//

    public   void   openLight();

    public   void   closeLight();

}
