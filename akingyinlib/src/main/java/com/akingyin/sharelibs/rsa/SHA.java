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

import java.security.MessageDigest;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/12 17:41
 * @ Version V1.0
 */
public class SHA {

  /**
   *
   * @param data to be encrypted
   * @param shaN encrypt method,SHA-1,SHA-224,SHA-256,SHA-384,SHA-512
   * @return 已加密的数据
   * @throws Exception
   */
  public static byte[] encryptSHA(byte[] data, String shaN) throws Exception {

    MessageDigest sha = MessageDigest.getInstance(shaN);
    sha.update(data);
    return sha.digest();

  }
}
