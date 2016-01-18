package com.md.appdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zlcd on 2016/1/11.
 */
public abstract  class RecyclerViewAdapterBase  <T,V extends View> extends RecyclerView.Adapter<ViewWrapper<V>>{

    private List<T>   items = new LinkedList<T>();

    private    RecyclerItemClickListener   itemClickListener;

    public RecyclerItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public    T   getItem(int postion){
        return  items.get(postion);
    }


    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }

    public  void  addItem(T  t){
        items.add(t);
    }

    public  void  addTopItem(T t){
        items.add(0,t);
    }

    public  void   addAll(List<T>  data){
        items.addAll(data);
    }

    public  void  removeItem(T  t){
        items.remove(t);
    }

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent,viewType));
    }

    @Override
    public void onBindViewHolder(ViewWrapper<V> holder, int position) {
        onBindView(items.get(position),holder.getView(),position,itemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  void   cleanItems(){
        items.clear();
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    protected abstract void  onBindView(T t,V v,int  postion,RecyclerItemClickListener  itemClickListener);



    public   interface  RecyclerItemClickListener{
        void  onItemClick(int  postion,View  view);

        void  onItemLongClick(int postion,View view);
    }
}
