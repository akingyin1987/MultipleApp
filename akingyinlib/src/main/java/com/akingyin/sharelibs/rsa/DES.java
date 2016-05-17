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
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/11 17:59
 * @ Version V1.0
 */
public class DES {

  public static final String ALGORITHM = "DES";

  public static byte[] decryptBASE64(String key) throws Exception {
    return Base64.decode(key, Base64.DEFAULT);
  }

  public static String encryptBASE64(byte[] key) throws Exception {
    return Base64.encodeToString(key, Base64.DEFAULT);
  }


  private   static  Key  toKey(String  key) throws Exception {
      return  toKey(key.getBytes());
  }

  /**
   * 将数据转成密钥key
   * @param key
   * @return
   * @throws Exception
   */
  private static Key toKey(byte[] key) throws Exception {
    // 从原始密钥数据创建DESKeySpec对象
    DESKeySpec dks = new DESKeySpec(key);

    // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
    SecretKey secretKey = keyFactory.generateSecret(dks);

    // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
    // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

    return secretKey;
  }

  /**
   * 解密
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static byte[] decrypt(byte[] data, String key) throws Exception {
    Key k = toKey(decryptBASE64(key));

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, k);

    return cipher.doFinal(data);
  }

  /**
   * 加密
   * @param data
   * @param key
   * @return
   * @throws Exception
   */
  public static byte[] encrypt(byte[] data, String key) throws Exception {

    //获取到密钥key
    Key k = toKey(decryptBASE64(key));

    //Cipher对象完成加密操作
    Cipher cipher = Cipher.getInstance(ALGORITHM);

    //用密钥初始化对象
    cipher.init(Cipher.ENCRYPT_MODE, k);

    //完成加密
    return cipher.doFinal(data);
  }

  public static String initKey() throws Exception {
    return initKey(null);
  }

  public static String initKey(String seed) throws Exception {
    SecureRandom secureRandom = null;
    // 生成一个可信任的随机数源
    if (seed != null) {
      secureRandom = new SecureRandom(decryptBASE64(seed));
    } else {
      secureRandom = new SecureRandom();
    }

    KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
    kg.init(secureRandom);

    SecretKey secretKey = kg.generateKey();

    return encryptBASE64(secretKey.getEncoded());
  }
}
