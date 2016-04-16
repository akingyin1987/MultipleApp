package com.akingyin.ui.adapter;

import android.content.Context;

import com.akingyin.sharelibs.adapter.SuperAdapter;
import com.akingyin.sharelibs.adapter.internal.SuperViewHolder;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;



import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserAdapter extends SuperAdapter<UserEntity> {

    public UserAdapter(Context context, List<UserEntity> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, UserEntity item) {
        holder.setText(R.id.tx_name,item.toString());
    }
}
