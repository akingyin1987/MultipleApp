package com.md.appdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zlcd on 2016/1/11.
 */
public class ViewWrapper<V extends View > extends RecyclerView.ViewHolder {

    public  V    view;
    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public  V   getView(){
        return  view;
    }
}
