package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.presenter.impl.RxBindPresenterImpl;
import com.akingyin.view.IRxBindView;
import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.R;

import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.TimeUnit;


import rx.functions.Action1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxViewFragment extends Fragment implements IRxBindView{

    IRxbindPresenter   presenter;
    public Button   rxview_one_btn,rxview_tow_btn;

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
        RxView.clicks(rxview_one_btn)
              .throttleFirst(1, TimeUnit.SECONDS)
              .subscribe(new Action1<Void>() {
                  @Override
                  public void call(Void aVoid) {
                      rxview_one_btn.setText("one"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                      showMessage("testOne");
                  }
              });

        RxView.clicks(rxview_tow_btn)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    rxview_one_btn.setText("tow"+ RandomStringUtils.random(4,"我是中国人我爱您"));
                    showMessage("testTow");
                }
            });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(View v, String message) {

    }
}
