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

package com.md.appdemo.jobmanager;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/1/19.
 */
public class AsyncJobManager<T>  implements AsyncJob.OnJobListion ,Runnable{

    public   static   final   int   nThreads = 3;

    private   final LinkedList<AsyncJob<T>>    jobLinkedList = new LinkedList<AsyncJob<T>>();

    private ExecutorService executorService;

    private  OnJobManagerListion    listion;
    
    

    public OnJobManagerListion getListion() {
		return listion;
	}

	public void setListion(OnJobManagerListion listion) {
		this.listion = listion;
	}

	//任务管理状态
    private AtomicBoolean   jobmanagerstatus = new AtomicBoolean(false);


    //当前任务总数
    public    int   getJobTotal(){
        return  jobLinkedList.size();
    }
    
    public    void  cleanAllJobs(){
    	if(!jobmanagerstatus.get()){
    		 for(AsyncJob<T>  job : jobLinkedList){
    	            job.onCancel();
    	        }
    	 jobLinkedList.clear();
    	}
    }

    private AtomicInteger progress = new AtomicInteger(0);

    private    AtomicInteger   error = new AtomicInteger(0);

    //当前任务失败数
    public  int   getErrorTotal(){
        int  eror = 0;
        for(AsyncJob<T>  job : jobLinkedList){
            if(job.getJobstatusEnum() == JobstatusEnum.Error){
                eror++;
            }
        }
        return  eror;
    }

    public  int  getProress(){
        int  proress = 0;
        for(AsyncJob<T>  job : jobLinkedList){
            if(job.getJobstatusEnum() != JobstatusEnum.Null){
                proress++;
            }
        }
        return  proress;
    }

    public AsyncJobManager(  ) {
        if(null == executorService){
            executorService = Executors.newFixedThreadPool(nThreads);
        }

    }

    public  AsyncJobManager(int  nThreads,OnJobManagerListion  listion){
        if(null == executorService){
            executorService = Executors.newFixedThreadPool(nThreads);
        }
        this.listion = listion;

    }


    public  synchronized   void    onAddTask(@NonNull AsyncJob<T>    job){
          jobLinkedList.add(job);
    }

    public  synchronized   void   onRemoveTask(@NonNull AsyncJob<T>    job){
        if(jobLinkedList.contains(job)){
            job.onCancel();
            jobLinkedList.remove(job);
        }
    }

    //执行任务
    public  synchronized   void    execute(){
        if(!jobmanagerstatus.get()){
            progress.getAndSet(getProress());
            error.getAndSet(getErrorTotal());
            jobmanagerstatus.set(true);
            for(AsyncJob<T>  job : jobLinkedList){
                if(job.getJobstatusEnum() == JobstatusEnum.Null){
                    job.setJobListion(this);
                    executorService.execute(job);
                }
            }
        }
    }

    //结束
    public synchronized  void  onDestory(){
        try{
            jobmanagerstatus.getAndSet(false);
            executorService.shutdown();
            for(AsyncJob<T>  job : jobLinkedList){
                job.onCancel();
            }
            jobLinkedList.clear();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public interface    OnJobManagerListion{
        void  onProgress(int total, int press, int error);

        void  onError(String message);

        void  onComplete();
    }

   //private static Handler uiHandler = new Handler(Looper.getMainLooper());
    @Override
    public void onResult(JobstatusEnum jobstatusEnum) {
    	
        progress.set(progress.get()+1);
       
        if(jobstatusEnum == JobstatusEnum.NetWorkError){
            jobmanagerstatus.getAndSet(false);
            onDestory();
            if(null != listion){
            	listion.onError("网络错误，请稍候再试");
            }
            return;
        }
        if(jobstatusEnum == JobstatusEnum.Error){
            error.getAndAdd(1);
        }
        if(null != listion){

        	 listion.onProgress(getJobTotal(),progress.get(),error.get());
        }
        if(progress.get() == getJobTotal()){
            jobmanagerstatus.getAndSet(false);
            onDestory();
            if(null != listion){
                listion.onComplete();
            }
        }

    }

    @Override
    public void run() {
        if(null != listion){
            if(jobmanagerstatus.get()){
                listion.onProgress(getJobTotal(),progress.get(),error.get());
            }else{
                listion.onError("网络错误，请稍候再试");
            }

        }
    }
}
