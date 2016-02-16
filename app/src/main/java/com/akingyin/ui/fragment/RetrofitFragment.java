package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akingyin.presenter.impl.RetrofitPresenterImpl;
import com.akingyin.view.IRetrofitView;
import com.md.multipleapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zlcd on 2016/2/16.
 */

@EFragment(R.layout.fragemnt_retrofit)
public class RetrofitFragment  extends Fragment implements IRetrofitView{

    @Bean
     RetrofitPresenterImpl    presenter;

    @ViewById
    public TextView retrofit_info;

    @ViewById
    public Button   retrofit_btn1;


    @AfterViews
    public   void   initView(){
        presenter.setRetrofitView(this);
        presenter.initialize(null,null);
    }


    @Click
    public  void  retrofit_btn1(){
        presenter.findUserInfo("akingyin1987");
    }

    @Override
    public void initialize(Bundle savedInstanceState, View view) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void printMessage(String message) {
        retrofit_info.setText(message);
    }

    @Override
    public void hideDialog() {

    }
}
