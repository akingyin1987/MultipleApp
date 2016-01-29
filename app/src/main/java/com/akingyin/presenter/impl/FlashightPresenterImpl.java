package com.akingyin.presenter.impl;

import android.os.Bundle;
import android.view.View;

import com.akingyin.FlashLight;
import com.akingyin.presenter.IFlashlightPesenter;
import com.akingyin.view.IFlashlightView;

/**
 * Created by zlcd on 2016/1/29.
 */
public class FlashightPresenterImpl  implements IFlashlightPesenter {

    private IFlashlightView   flashlightView;

    private FlashLight   flashLight;

    public FlashightPresenterImpl(IFlashlightView flashlightView) {
        this.flashlightView = flashlightView;
        flashLight = new FlashLight();
    }

    @Override
    public void initialize(Bundle savedInstanceState,View view) {
        flashlightView.initialize(savedInstanceState,view);
    }

    @Override
    public void openLight() {
            System.out.println("openlight");
           flashLight.turnOnFlashLight();
           flashlightView.openLight();
    }

    @Override
    public void closeLight() {
        System.out.println("colse");
        flashLight.turnOffFlashLight();
        flashlightView.closeLight();
    }

    @Override
    public void onDestory() {
        flashLight.onDestory();
    }
}
