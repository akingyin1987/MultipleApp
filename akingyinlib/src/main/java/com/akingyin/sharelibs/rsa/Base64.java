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

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/11 17:58
 * @ Version V1.0
 */
public class base64 {

  /**
   * BASE64解密
   *
   * @param key the String to be decrypted
   * @return byte[] the data which is decrypted
   * @throws Exception
   */
  public static byte[] decryptBASE64(String key) throws Exception {
    return Base64.decode(key, Base64.DEFAULT);
  }
  /**
   * BASE64加密
   *
   * @param key the String to be encrypted
   * @return String the data which is encrypted
   * @throws Exception
   */
  public static String encryptBASE64(byte[] key) throws Exception {
    return Base64.encodeToString(key, Base64.DEFAULT);
  }
}
