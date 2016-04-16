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


/**
 * @ Description:
 * 创建一个泛型类来把所有类型的View包装成ViewHonlder
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/4/16 10:26
 * @ Version V1.0
 */
public class AAViewWrapper<V extends  View>   extends RecyclerView.ViewHolder {

  private    V     view;

  public AAViewWrapper(V itemView) {
    super(itemView);
    this.view = itemView;
  }

  public V getView() {
    return view;
  }


}
