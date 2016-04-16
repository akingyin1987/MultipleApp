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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

/**
 * @ Description:
 *  基于AA插件的RecyclerView 适配器
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/4/16 10:47
 * @ Version V1.0
 */
public abstract  class AARecyclerViewAdapterBase<T,V extends View>  extends RecyclerView.Adapter<AAViewWrapper<V>>{

  protected List<T>    items = new LinkedList<>();

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  @Override
  public AAViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
    return new AAViewWrapper<>(onCreateItemView(parent,viewType));
  }

  @Override
  public void onBindViewHolder(AAViewWrapper<V> holder, int position) {

  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  /**
   * 创建视图Item，交给具体实现类完成
   * @param parent
   * @param viewType
   */
  protected   abstract   V  onCreateItemView(ViewGroup parent, int viewType);

}
