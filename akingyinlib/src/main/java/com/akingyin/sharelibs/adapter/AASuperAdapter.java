/*
 *
 *   Copyright (c) 2016 [akingyin@163.com]
 *
 *   Licensed under the Apache License, Version 2.0 (the "License”);
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.akingyin.sharelibs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.akingyin.sharelibs.adapter.internal.AABaseSuperAdapter;
import com.akingyin.sharelibs.adapter.internal.BaseViewHolder;
import com.akingyin.sharelibs.adapter.internal.CRUD;
import java.util.List;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/4/16 15:10
 * @ Version V1.0
 */
public  abstract class AASuperAdapter<T,V extends View>  extends AABaseSuperAdapter<T,V> implements
    CRUD<T> {

  public AASuperAdapter(Context context) {
    super(context);
  }

  @Override
  public final void add(T item) {
    mList.add(item);
    int index = mList.size() - 1;
    if (hasHeaderView())
      index++;
    notifyItemInserted(index);

  }

  @Override
  public final void insert(int index, T item) {
    mList.add(index, item);
    if (hasHeaderView())
      index++;
    notifyItemInserted(index);

  }

  @Override
  public final void addAll(List<T> items) {
    if (items == null || items.size() == 0) {
      return;
    }
    int start = mList.size();
    mList.addAll(items);
    if (hasHeaderView())
      start++;
    notifyItemRangeInserted(start, items.size());

  }

  @Override
  public final void remove(T item) {
    if (contains(item)) {
      int index = mList.indexOf(item);
      remove(index);
    }
  }

  @Override
  public final void remove(int index) {
    mList.remove(index);
    if (hasHeaderView())
      index++;
    notifyItemRemoved(index);

  }

  @Override
  public final void set(T oldItem, T newItem) {
    set(mList.indexOf(oldItem), newItem);
  }

  @Override
  public final void set(int index, T item) {
    mList.set(index, item);
    if (hasHeaderView())
      index++;
    notifyItemChanged(index);

  }

  @Override
  public final void replaceAll(List<T> items) {
    clear();
    addAll(items);
  }

  @Override
  public final boolean contains(T item) {
    return mList.contains(item);
  }

  @Override
  public final void clear() {
    mList.clear();
    notifyDataSetChanged();

  }

  @Override
  public void addTop(T item) {
    mList.add(0,item);
  }

  @Override
  public void addAllTop(List<T> items) {
    mList.addAll(0,items);
  }

  @Override
  public BaseViewHolder<V> onCreate(View convertView, ViewGroup parent, int viewType) {
    return new BaseViewHolder<>(onCreateItemView(parent,viewType));

  }

  @Override
  public void onBind(BaseViewHolder<V> holder, int viewType, int position, T item) {

        onBindData(holder,viewType,position,item);
  }


  /**
   * 创建视图Item，交给具体实现类完成
   * @param parent
   * @param viewType
   */
  protected   abstract   V  onCreateItemView(ViewGroup parent, int viewType);

  /**
   * 绑定数据
   * @param holder
   * @param viewType
   * @param position
   * @param item
   */
  protected   abstract   void   onBindData(BaseViewHolder<V> holder, int viewType, int position, T item);
}
