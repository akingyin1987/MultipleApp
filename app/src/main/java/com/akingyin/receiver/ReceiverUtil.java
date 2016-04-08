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

package com.akingyin.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/4/8 13:11
 * @ Version V1.0
 */
public class ReceiverUtil {

  public   static   void   sendAppReceiver(Context   context,String  action){
    Intent   intent = new Intent();
    intent.setAction(action);
    Bundle   data = new Bundle();
    data.putString("app",System.currentTimeMillis()+"time");
    context.sendBroadcast(intent);
  }

}
