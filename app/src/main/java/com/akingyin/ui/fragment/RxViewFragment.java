package com.akingyin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akingyin.presenter.IRxbindPresenter;
import com.akingyin.presenter.impl.RxBindPresenterImpl;
import com.akingyin.view.IRxBindView;
import com.md.multipleapp.R;

/**
 * Created by zlcd on 2016/2/4.
 */
public class RxViewFragment extends Fragment implements IRxBindView{

    IRxbindPresenter   presenter;

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

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(View v, String message) {

    }
}
