package com.md.appdemo.jobmanager;

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
    public   abstract   void    onCancel();

    //执行任务
    public   abstract    void     doOnBackground();


    @Override
    public void run() {
           if(jobstatusEnum == JobstatusEnum.Null){
               doOnBackground();
           }
    }


    //任务监听器
    public   interface   OnJobListion{
        void   onResult(JobstatusEnum  jobstatusEnum);
    }
}
