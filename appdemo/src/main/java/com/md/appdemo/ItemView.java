package com.md.appdemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.md.appdemo.model.UserEntity;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zlcd on 2016/1/11.
 */

@EViewGroup(R.layout.list_item)
public class ItemView  extends CardView  implements View.OnClickListener,View.OnLongClickListener{

    @ViewById
    public TextView  item_data;

    public ItemView(Context context) {
        super(context);
        this.setOnClickListener(this);
        this.setOnLongClickListener(this);
    }

    private RecyclerViewAdapterBase.RecyclerItemClickListener   itemClickListener;

    public RecyclerViewAdapterBase.RecyclerItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(RecyclerViewAdapterBase.RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private   int  postion;

    public   void   onBind(UserEntity userEntity,int postion){
        this.postion = postion;
        item_data.setText(userEntity.toString());
    }

    @Override
    public void onClick(View v) {
        if(null != itemClickListener){
            itemClickListener.onItemClick(postion,this);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(null != itemClickListener){
            itemClickListener.onItemLongClick(postion,this);
            return  true;
        }
        return false;
    }
}
