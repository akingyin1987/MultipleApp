package com.akingyin.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.presenter.impl.RxBindPresenterImpl;
import com.akingyin.view.IRxBindView;
import com.desmond.squarecamera.CameraActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.R;
import com.ragnarok.rxcamera.RxCamera;
import com.ragnarok.rxcamera.RxCameraData;
import com.ragnarok.rxcamera.config.RxCameraConfig;
import com.ragnarok.rxcamera.config.RxCameraConfigChooser;
import com.ragnarok.rxcamera.request.Func;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;


import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxViewFragment extends Fragment implements IRxBindView{

    IRxbindPresenter   presenter;
    public Button   rxview_one_btn,rxview_tow_btn,rxview_each,rxview_map;
    public  TextureView rxb_texture;
    private RxCamera camera;
    public TextView  rxview_info;
    public   static   RxViewFragment   newInstance(){
        RxViewFragment   fragment = new RxViewFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_rxbind,null);
      return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.initialize(savedInstanceState,view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RxBindPresenterImpl(this);
    }

    @Override
    public void initialize(Bundle savedInstanceState, View view) {
        rxview_one_btn = (Button)view.findViewById(R.id.rxview_one_btn);
        rxview_tow_btn = (Button)view.findViewById(R.id.rxview_tow_btn);
        rxview_info = (TextView)view.findViewById(R.id.rxview_info);
        rxview_each = (Button)view.findViewById(R.id.rxview_each);
        rxb_texture= (TextureView)view.findViewById(R.id.rxb_texture);
        rxview_map =(Button)view.findViewById(R.id.rxview_map);
        RxView.clicks(rxview_map).throttleFirst(1,TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.rxMap();
            }
        });
        RxView.clicks(rxview_each).throttleFirst(1,TimeUnit.SECONDS)
              .subscribe(new Action1<Void>() {
                  @Override
                  public void call(Void aVoid) {
                      presenter.rxEach();
                  }
              });

        RxView.clicks(rxview_one_btn)
              .throttleFirst(1, TimeUnit.SECONDS)
              .subscribe(new Action1<Void>() {
                  @Override
                  public void call(Void aVoid) {
                      if(null == camera){
                          openCamera();
                          return;
                      }
                    //  rxview_one_btn.setText("one"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                    //  showMessage(rxview_one_btn,"testOne");
                      if(null != camera){
                          camera.request().takePictureRequest(true, new Func() {
                              @Override
                              public void call() {

                              }
                          },960,540, ImageFormat.JPEG)
                              .subscribe(new Action1<RxCameraData>() {
                              @Override
                              public void call(RxCameraData rxCameraData) {
                                  String path = Environment.getExternalStorageDirectory() + "/test.jpg";
                                  File file = new File(path);
                                  Bitmap bitmap = BitmapFactory.decodeByteArray(rxCameraData.cameraData, 0, rxCameraData.cameraData.length);
                                  bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                                      rxCameraData.rotateMatrix, false);
                                  try {
                                      file.createNewFile();
                                      FileOutputStream fos = new FileOutputStream(file);
                                      bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                                      fos.close();
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          });
                      }
                  }
              });

        RxView.clicks(rxview_tow_btn)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Intent   intent  =  new Intent(getContext(), CameraActivity.class);
                    startActivityForResult(intent,100);
                    rxview_one_btn.setText("tow"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                }
            });


    }


    public   void  openCamera(){
        RxCameraConfig config = RxCameraConfigChooser.obtain().
            useBackCamera().
            setAutoFocus(true).
            setPreferPreviewFrameRate(15, 30).
            setPreferPreviewSize(new Point(960, 540)).
            setHandleSurfaceEvent(true).
            get();
        RxCamera.open(getContext(),config).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {
                return rxCamera.bindTexture(rxb_texture);
            }
        }).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {
                return rxCamera.startPreview();
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<RxCamera>() {
            @Override
            public void onCompleted() {
                 System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                 System.out.println(e.getMessage());
            }

            @Override
            public void onNext(RxCamera rxCamera) {
                  System.out.println("next");
                camera = rxCamera;
            }
        });
    }

    @Override
    public void showMessage(String message) {
        new MaterialIntroView.Builder(getActivity())
            .enableDotAnimation(false)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(Focus.ALL)
            .setDelayMillis(200)
            .enableFadeAnimation(true)
            .performClick(true)
            .setInfoText(message)
            .show();
    }

    @Override
    public void showMessage(View v, String message) {
        new MaterialIntroView.Builder(getActivity())
            .enableDotAnimation(false)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(Focus.ALL)
            .setDelayMillis(200)
            .enableFadeAnimation(true)
            .performClick(true)
            .setTarget(v)
            .setInfoText(message)
            .show();
    }

    @Override
    public void printMessage(String message) {
        rxview_info.setText(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != camera){
            camera.closeCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("request_code="+requestCode+":"+resultCode);
    }
}
