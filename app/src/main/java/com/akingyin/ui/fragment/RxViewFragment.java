package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.presenter.impl.RxBindPresenterImpl;
import com.akingyin.view.IRxBindView;
import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.R;

import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.TimeUnit;


import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import rx.functions.Action1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxViewFragment extends Fragment implements IRxBindView{

    IRxbindPresenter   presenter;
    public Button   rxview_one_btn,rxview_tow_btn,rxview_each;

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
                      rxview_one_btn.setText("one"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                      showMessage(rxview_one_btn,"testOne");
                  }
              });

        RxView.clicks(rxview_tow_btn)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    rxview_one_btn.setText("tow"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                    showMessage(rxview_tow_btn,"testTow");
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
}
