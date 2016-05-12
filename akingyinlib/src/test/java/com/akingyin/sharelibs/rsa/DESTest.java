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

package com.akingyin.sharelibs.rsa;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.text.TextUtils;
import android.util.Base64;
import com.akingyin.sharelibs.jlog.JLog;
import com.akingyin.sharelibs.utils.CommonUtils;
import com.akingyin.sharelibs.utils.TLog;
import java.net.URLDecoder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/12 15:35
 * @ Version V1.0
 */
public class DESTest  {

  public   String    key="1234567890abcdef";

  @Before public void setUp() throws Exception {

        key="1234567890abcdef";
  }


  @Test
  public void testDecryptBASE64() throws Exception {
    assertEquals(null == key,false);

    TLog.d("test","test");
    CommonUtils.convertTime(1000000);
    System.out.println(CommonUtils.decodeUnicodeStr(key));
     System.out.println(URLDecoder.decode(key,"utf-8"));
     System.out.println(key);
     System.out.println(TextUtils.isEmpty(key)+":"+key.getBytes().length);
     System.out.println(null == Base64.encode(key.getBytes(),Base64.DEFAULT));

     System.out.println(Base64.encodeToString(key.getBytes(),Base64.DEFAULT));

    assertEquals(key == null,false);
  }

  @Test public void testEncryptBASE64() throws Exception {

  }

  @Test public void testDecrypt() throws Exception {

  }

  @Test public void testEncrypt() throws Exception {

  }

  @Test public void testInitKey() throws Exception {

  }

  @Test public void testInitKey1() throws Exception {

  }
}