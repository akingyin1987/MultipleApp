package com.md.appdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.md.appdemo.model.UserEntity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by zlcd on 2016/1/11.
 */

@EBean
public class AAUserAdapter extends  RecyclerViewAdapterBase<UserEntity,ItemView> {

    @RootContext
    Context   context;

    @Override
    protected ItemView onCreateItemView(ViewGroup parent, int viewType) {
        return com.md.appdemo.ItemView_.build(context);
    }

    @Override
    protected void onBindView(UserEntity userEntity,  ItemView itemView,  int postion,  RecyclerItemClickListener  itemClickListener) {
        itemView.setItemClickListener(itemClickListener);
        itemView.onBind(userEntity,postion);
    }
}
