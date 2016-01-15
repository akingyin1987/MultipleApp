package com.md.appdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.md.appdemo.R;

import java.util.List;

/**
 * Created by zlcd on 2016/1/15.
 */
public class HorizontalAdapter extends BaseAdapter {

    public Context   context;

    public List<HorItemData>  items;

    private   HorItemData   selectItem;

    public HorizontalAdapter(@NonNull Context context, @NonNull List<HorItemData> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        if(null != items){
            return  items.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null != items){
            return  items.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HorViewHolder   holder = null;

        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.hor_itemview,null);
            holder = new HorViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.hor_item);
            convertView.setTag(holder);

        }else{
            holder = (HorViewHolder)convertView.getTag();
        }
        if(null != selectItem){
            if(items.get(position) == selectItem){
                holder.textView.setTextColor(Color.GRAY);
            }else{
                holder.textView.setTextColor(Color.BLACK);
            }

        }else{
            holder.textView.setTextColor(Color.BLACK);
        }
        holder.textView.setText(items.get(position).itemName);
        return convertView;
    }

    final  static class   HorViewHolder{
        TextView    textView;
    }
}
