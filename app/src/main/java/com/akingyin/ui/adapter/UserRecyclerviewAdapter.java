package com.akingyin.ui.adapter;

import android.content.Context;

import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

import org.byteam.superadapter.recycler.BaseViewHolder;
import org.byteam.superadapter.recycler.SuperAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public class UserRecyclerviewAdapter extends SuperAdapter<UserEntity> {

    public UserRecyclerviewAdapter(Context context, List<UserEntity> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(int viewType, BaseViewHolder holder, int position, UserEntity item) {
         holder.setText(R.id.tx_name,item.userName);
    }
}
