package com.akingyin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.md.multipleapp.R;
import com.md.multipleapp.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class UserSwipeAdapter  extends RecyclerSwipeAdapter<UserSwipeAdapter.SimpleViewHolder> {

    private List<UserEntity>   items =  new ArrayList<>();

    private Context   context;

    private SwipeLayout.SwipeListener   listener;

    public List<UserEntity> getItems() {
        return items;
    }

    public void setItems(List<UserEntity> items) {
        this.items = items;
    }

    public SwipeLayout.SwipeListener getListener() {
        return listener;
    }

    public void setListener(SwipeLayout.SwipeListener listener) {
        this.listener = listener;
    }

    public UserSwipeAdapter(List<UserEntity> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public   void  add(UserEntity userEntity){
        items.add(0,userEntity);
        notifyDataSetChanged();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;


        public SimpleViewHolder(View itemView,SwipeLayout.SwipeListener listener) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewPos = (TextView) itemView.findViewById(R.id.tx_name);
            swipeLayout.addSwipeListener(listener);

        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.item_user,null);

        return new SimpleViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int position) {
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.textViewPos.setText(items.get(position).toString());

        mItemManger.bindView(viewHolder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
