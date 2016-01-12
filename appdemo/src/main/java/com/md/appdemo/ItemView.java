package com.md.appdemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.md.appdemo.model.UserEntity;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by zlcd on 2016/1/11.
 */

@EViewGroup(R.layout.list_item)
public class ItemView  extends CardView {

    @ViewById
    public TextView  item_data;

    @ViewById
    public TagGroup    tag_group;

    public ItemView(Context context) {
        super(context);
    }

    public   void   onBind(UserEntity userEntity){
        item_data.setText(userEntity.toString());
        tag_group.setTags(String.valueOf(userEntity.Id));

    }

}
