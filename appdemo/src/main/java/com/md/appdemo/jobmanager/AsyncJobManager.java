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
public  class AsyncJobManager<T>  implements AsyncJob.OnJobListion ,Runnable{

    public   static   final   int   nThreads = 3;

    private   final LinkedList<AsyncJob<T>>    jobLinkedList = new LinkedList<>();

    private ExecutorService executorService;

    private  OnJobManagerListion    listion;

    //任务管理状态
    private AtomicBoolean   jobmanagerstatus = new AtomicBoolean(false);


    //当前任务总数
    public    int   getJobTotal(){
        return  jobLinkedList.size();
    }

    private AtomicInteger progress = new AtomicInteger(0);

    private    AtomicInteger   error = new AtomicInteger(0);

    //当前任务失败数
    public  int   getErrorTotal(){
        int  eror = 0;
        for(AsyncJob  job : jobLinkedList){
            if(job.getJobstatusEnum() == JobstatusEnum.Error){
                eror++;
            }
        }
        return  eror;
    }

    public  int  getProress(){
        int  proress = 0;
        for(AsyncJob  job : jobLinkedList){
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
            progress.set(getProress());
            error.set(getErrorTotal());
            jobmanagerstatus.set(true);
            for(AsyncJob  job : jobLinkedList){
                if(job.getJobstatusEnum() == JobstatusEnum.Null){
                    job.setJobListion(this);
                    executorService.execute(job);
                }
            }
        }
    }

    //结束
    public synchronized  void  onDestory(){
        jobmanagerstatus.set(false);
        executorService.shutdown();
        for(AsyncJob  job : jobLinkedList){
            job.onCancel();
        }
        jobLinkedList.clear();

    }

    public interface    OnJobManagerListion{
        void  onProgress(int  total,int press,int error);

        void  onError(String message);
    }

    private static Handler uiHandler = new Handler(Looper.getMainLooper());
    @Override
    public void onResult(JobstatusEnum jobstatusEnum) {
        progress.set(progress.get()+1);
        if(jobstatusEnum == JobstatusEnum.NetWorkError){
            jobmanagerstatus.set(false);
            onDestory();
            return;
        }
        if(jobstatusEnum == JobstatusEnum.Error){
            error.set(error.get() + 1);
        }
        if(null != listion){

            uiHandler.post(this);
        }
        if(progress.get() == getJobTotal()){
            jobmanagerstatus.set(false);
            onDestory();
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
