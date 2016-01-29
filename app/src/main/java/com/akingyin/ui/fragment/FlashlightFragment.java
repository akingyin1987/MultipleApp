package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.akingyin.presenter.impl.FlashightPresenterImpl;
import com.akingyin.view.IFlashlightView;
import com.md.multipleapp.R;

/**
 * Created by zlcd on 2016/1/29.
 */
public class FlashlightFragment  extends Fragment  implements IFlashlightView {

    public   static   FlashlightFragment   newInstance(){
        FlashlightFragment   fragment = new FlashlightFragment();
        return  fragment;
    }

    public ImageView  flashlight_lamp,flashlight_showoff;

    private FlashightPresenterImpl   presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View   view = inflater.inflate(R.layout.fragment_flashlight,null);

        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FlashightPresenterImpl(this);
        presenter.initialize(savedInstanceState,view);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView");
        presenter.onDestory();
    }

    @Override
    public void initialize(Bundle savedInstanceState,View view) {
        flashlight_lamp = (ImageView)view.findViewById(R.id.flashlight_lamp);
        flashlight_showoff = (ImageView)view.findViewById(R.id.flashlight_showoff);
        flashlight_showoff.setTag(1);
        flashlight_showoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    int  tag = Integer.parseInt(flashlight_showoff.getTag().toString());
                    System.out.println("tag="+tag);
                    if(tag == 1){
                       presenter.openLight();
                    }else{
                        presenter.closeLight();
                    }
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void openLight() {
        System.out.println("open-tag=2");
        flashlight_showoff.setTag(2);
        flashlight_showoff.setImageResource(R.drawable.shou_on);
        flashlight_lamp.setImageResource(R.drawable.bg1);

    }

    @Override
    public void closeLight() {

        flashlight_showoff.setImageResource(R.drawable.shou_off);
        flashlight_lamp.setImageResource(R.drawable.bg);
        flashlight_showoff.setTag(1);
    }
}
