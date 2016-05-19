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

package com.akingyin.jobmanagers;

import com.activeandroid.ActiveAndroid;
import com.md.multipleapp.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/5/17 16:42
 * @ Version V1.0
 */
public class Testjob extends  AsyncJob<Integer> {

  private List<UserEntity>  datas;

  private    boolean    cancel = false;

  public Testjob(Integer integer) {
    super(integer);

  }

  @Override public void onCancel() {
      super.onCancel();
      System.out.println("onCancel");
      cancel = true;
  }

  @Override public void doOnBackground() {
    if(cancel){
      return;
    }
    System.out.println("data="+getT());
        try{
          ActiveAndroid.beginTransaction();
          for(UserEntity  userEntity : datas){
             userEntity.save();
          }
          ActiveAndroid.setTransactionSuccessful();
          onSuccess();
        }catch (Exception e){
          System.out.println("onError");
          e.printStackTrace();
          onError();
        }finally {
          ActiveAndroid.endTransaction();
        }
  }

  private   JobstatusEnum   jobstatusEnum = JobstatusEnum.Null;

  @Override
  public JobstatusEnum onNext() {
    System.out.println("onNext="+getT());
    if(cancel){
      return JobstatusEnum.Error;
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Random  random  = new Random();
    jobstatusEnum = JobstatusEnum.Runing;
    if(random.nextInt(2)==1){
      jobstatusEnum = JobstatusEnum.Success;
    }
    jobstatusEnum = JobstatusEnum.Success;
    System.out.println(jobstatusEnum.toString());
    return jobstatusEnum;
  }

  @Override public boolean onBefore() {
    System.out.println("onBefore--");
    datas = new ArrayList<>();
    Random  random = new Random();
    for(int i=0;i<10000;i++){
      UserEntity  userEntity = new UserEntity();
      userEntity.userName=getT()+"-"+random.nextInt(1000);
      userEntity.age = random.nextInt(1000);

    }
    return true;
  }

  @Override public void onAfter() {
       System.out.println("success="+getT());
  }
}
