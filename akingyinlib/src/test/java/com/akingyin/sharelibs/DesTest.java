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

package com.akingyin.sharelibs;

import android.test.InstrumentationTestCase;
import android.text.TextUtils;
import android.util.Base64;
import com.akingyin.sharelibs.rsa.DES;
import org.junit.Test;


/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/11 18:11
 * @ Version V1.0
 */
public class DesTest extends InstrumentationTestCase{

  public   static      String   key="1234567890";

  public   String   str = "test";



  @Test
  public   void    testDes() throws Exception {
       assertEquals(TextUtils.isEmpty(key),true);
       byte[] keydata = Base64.encode(key.getBytes(),Base64.DEFAULT);
       key = new String(keydata,"utf-8");
       System.out.println(key);
       byte[]  data = DES.encrypt(str.getBytes(),key);
       System.out.println(Base64.encode(data,Base64.DEFAULT));

         assertEquals(4, 2 + 2);

  }
}
