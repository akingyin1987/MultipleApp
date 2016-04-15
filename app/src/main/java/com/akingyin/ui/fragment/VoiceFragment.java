package com.akingyin.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialcamera.MaterialCamera;
import com.akingyin.sharelibs.utils.TLog;
import com.akingyin.widget.NumberKeyboardView;
import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.R;


import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class VoiceFragment extends Fragment {

    public   static   VoiceFragment   newInstance(){
        VoiceFragment   fragment = new VoiceFragment();
        return  fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View   view   = inflater.inflate(R.layout.fragment_voice,null);
        return  view;
    }

    TextView   tv_keyboard;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxView.clicks(view.findViewById(R.id.voice_add))
            .throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        RxView.clicks(view.findViewById(R.id.material_camera)).throttleFirst(1, TimeUnit.SECONDS)
              .subscribe(new Action1<Void>() {
                  @Override
                  public void call(Void aVoid) {
                      startCamera();
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      throwable.printStackTrace();
                      TLog.d("出错了" + throwable.getMessage());
                  }
              });
        tv_keyboard = (TextView)view.findViewById(R.id.tv_keyboard);
        NumberKeyboardView   keyboardView = (NumberKeyboardView)view.findViewById(R.id.keyboard);
        keyboardView.setListion(new NumberKeyboardView.KeyboardListion() {
            @Override
            public void onKeyboard(String keyNum) {
                tv_keyboard.setText(tv_keyboard.getText()+keyNum);
            }

            @Override
            public void onDelect() {
               String  context = tv_keyboard.getText().toString();
                if(!TextUtils.isEmpty(context)){
                    tv_keyboard.setText(context.substring(0,context.length()-1));
                }
            }

            @Override
            public void onClean() {
                  tv_keyboard.setText("");
            }
        });

    }

    private final static int CAMERA_RQ = 6969;
    public   void   startCamera(){
        File saveFolder = new File(Environment.getExternalStorageDirectory(), "camera_temp");
        if(!saveFolder.exists() || !saveFolder.isDirectory()){
            saveFolder.mkdirs();
        }
        new MaterialCamera(getActivity()).allowRetry(true).autoSubmit(false)
            .saveDir(saveFolder)
            .showPortraitWarning(true)
            .defaultToFrontFacing(false)
            .retryExits(false)
            .start(CAMERA_RQ);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TLog.d("requestcode="+requestCode+":"+resultCode);
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 42);
        }
    }


}
