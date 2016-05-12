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

import android.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/12 17:33
 * @ Version V1.0
 */
public class HMAC {

  public static byte[] decryptBASE64(String key) throws Exception {
    return Base64.decode(key, Base64.DEFAULT);
  }
  public static String encryptBASE64(byte[] key) throws Exception {
    return Base64.encodeToString(key, Base64.DEFAULT);
  }

  /**
   * 初始化HMAC密钥
   *
   * @return
   * @throws Exception
   */
  public static String initMacKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");

    SecretKey secretKey = keyGenerator.generateKey();
    return encryptBASE64(secretKey.getEncoded());
  }

  /**
   * HMAC加密
   *
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static byte[] encryptHMAC(byte[] data, String key, String method) throws Exception {

    //SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacMD5");
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), method);//直接用给定的字符串进行
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    mac.init(secretKey);

    return mac.doFinal(data);

  }
}
