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

/**
 * Created by Administrator on 2016/1/19.
 */
public abstract class AsyncJob<T>  implements  Runnable {

    private    T     t;

    private   OnJobListion   jobListion;


    public OnJobListion getJobListion() {
        return jobListion;
    }

    public void setJobListion(OnJobListion jobListion) {
        this.jobListion = jobListion;
    }

    public AsyncJob(T t, OnJobListion jobListion) {
        this.t = t;
        this.jobListion = jobListion;
    }

    public AsyncJob(T t) {
        this.t = t;
    }


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    //任务当前状态
    private    JobstatusEnum   jobstatusEnum = JobstatusEnum.Null;

    public JobstatusEnum getJobstatusEnum() {
        return jobstatusEnum;
    }

    public void setJobstatusEnum(JobstatusEnum jobstatusEnum) {
        this.jobstatusEnum = jobstatusEnum;
    }

    //取消任务
    public      void    onCancel(){
      jobstatusEnum = JobstatusEnum.Cancel;

    }

    //执行任务
    public   abstract    void     doOnBackground();
    
    //下一次
    public   abstract    JobstatusEnum      onNext();

    //之前处理
    public   abstract    boolean     onBefore();
    
    //之后
    public   abstract    void    onAfter();
    
    @Override
    public void run() {
            if(jobstatusEnum == JobstatusEnum.Cancel){
              return;
            }
           if(jobstatusEnum == JobstatusEnum.Null){
        	   if(onBefore()){  
        		   JobstatusEnum  nextstatus = null;
        		   while((nextstatus = onNext()) == JobstatusEnum.Runing){
                 if(jobstatusEnum == JobstatusEnum.Cancel){
                   break;
                 }
               }

        		   if(nextstatus == JobstatusEnum.Success){
        			   doOnBackground();
        			   onAfter();
        		   }else{
        			   onError();
        			   return;
        		   }
        		  
        	   }else{
        		   onError();
        	   }
              
           }
    }


    //任务监听器
    public   interface   OnJobListion{
        void   onResult(JobstatusEnum jobstatusEnum);
    }
    
    public  void   onSuccess(){
      if(jobstatusEnum == JobstatusEnum.Cancel){
        return;
      }
    	jobstatusEnum = JobstatusEnum.Success;
    	if(null != jobListion){
    		jobListion.onResult(JobstatusEnum.Success);
    	}
    }
    
    public  void   onError(){
      if(jobstatusEnum == JobstatusEnum.Cancel){
        return;
      }
    	jobstatusEnum = JobstatusEnum.Error;
    	if(null != jobListion){
    		jobListion.onResult(JobstatusEnum.Error);
    	}
    }
    
    public  void   onNetWorkError(){
      if(jobstatusEnum == JobstatusEnum.Cancel){
        return;
      }
    	jobstatusEnum = JobstatusEnum.NetWorkError;
    	if(null != jobListion){
    		jobListion.onResult(JobstatusEnum.NetWorkError);
    	}
    }
}
