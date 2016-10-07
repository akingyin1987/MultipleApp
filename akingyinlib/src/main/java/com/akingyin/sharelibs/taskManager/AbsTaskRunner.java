package com.akingyin.sharelibs.taskManager;

import com.akingyin.sharelibs.jlog.JLog;
import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;

import java.util.LinkedList;
import java.util.List;

import java.util.UUID;



/**
 * 执行的任务
 * Created by Administrator on 2016/7/3.
 */
public  abstract class AbsTaskRunner implements Runnable{

    private     String   Tag = UUID.randomUUID().toString();//任务唯一ID标识


    public String getTag() {
        return Tag;
    }

    private TaskStatusEnum    taskStatusEnum = TaskStatusEnum.NULL;

    public TaskStatusEnum getTaskStatusEnum() {
        return taskStatusEnum;
    }

    public void setTaskStatusEnum(TaskStatusEnum taskStatusEnum) {
        this.taskStatusEnum = taskStatusEnum;
    }

    //子任务只需要依次执行与当前任务在同一线程
    private List<AbsTaskRunner> queueTasks = new LinkedList<>();

    //获取子任务
    public List<AbsTaskRunner> getQueueTasks() {
        return queueTasks;
    }

    public   void    addTask(AbsTaskRunner   taskRunner){
        queueTasks.add(taskRunner);
    }

    public   void   addTasks(List<AbsTaskRunner>   taskRunners){
        queueTasks.addAll(taskRunners);
    }

    private  ITaskResultCallBack     callBack;

    public ITaskResultCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ITaskResultCallBack callBack) {
        this.callBack = callBack;
    }

    //错误信息描述
    private     String    errorMsg;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        JLog.d("errorMsg="+errorMsg);
        this.errorMsg = errorMsg;
    }

    public   void    TaskDoing(){
        taskStatusEnum = TaskStatusEnum.DOING;
    }

    public   void    TaskError(){
        taskStatusEnum = TaskStatusEnum.ERROR;
        if(null != callBack){
            callBack.onCallBack(taskStatusEnum,errorMsg);
        }
    }

    public   void    TaskOnNetError(){
        taskStatusEnum = TaskStatusEnum.NETERROR;
        errorMsg ="网络错误，请稍候再试";
        if(null != callBack){
            callBack.onCallBack(taskStatusEnum,errorMsg);
        }
    }

    public  void   TaskOnDoing(){
        if(taskStatusEnum != TaskStatusEnum.CANCEL){
            taskStatusEnum = TaskStatusEnum.DOING;
        }

    }

    public  void  TaskOnSuccess(){

        taskStatusEnum = TaskStatusEnum.SUCCESS;
        if(null != callBack){
            callBack.onCallBack(taskStatusEnum,errorMsg);
        }
    }

    //取消任务
    public     void onCancel(){
        JLog.d("Task-onCancel");
      for(AbsTaskRunner   taskRunner : queueTasks){
          taskRunner.onCancel();
      }
        queueTasks.clear();
        if(taskStatusEnum == TaskStatusEnum.NULL || taskStatusEnum == TaskStatusEnum.WAITING
                || taskStatusEnum == TaskStatusEnum.DOING){
            taskStatusEnum = TaskStatusEnum.CANCEL;
        }


    }






    //任务执行前处理
    public  abstract   TaskStatusEnum  onBefore();


    public  abstract  TaskStatusEnum   onToDo();


    private      TaskStatusEnum  doBackground(){

        TaskOnDoing();
        TaskStatusEnum  temp  = onBefore();
        if(temp == TaskStatusEnum.SUCCESS){
            temp = doSonTaskBackground();
        }
        if(temp == TaskStatusEnum.SUCCESS){
            return  onToDo();
        }
        if(temp == TaskStatusEnum.DOING || temp == TaskStatusEnum.WAITING){
            return  TaskStatusEnum.ERROR;
        }
        return  temp;

    }



    @Override
    public void run() {

       if(taskStatusEnum == TaskStatusEnum.CANCEL){
           System.out.println("取消");
           return;
       }

       taskStatusEnum =  doBackground();
        System.out.println("run==="+getTag()+":"+taskStatusEnum.getName());
       switch (taskStatusEnum){
           case SUCCESS:
               TaskOnSuccess();
               break;
           case ERROR:
               TaskError();
               break;
           case NETERROR:
               TaskOnNetError();
               break;
       }

    }

    int   index=0;
    public    TaskStatusEnum    doSonTaskBackground(){
        if(queueTasks.size() == 0 || index >= queueTasks.size()){
            return  TaskStatusEnum.SUCCESS;
        }
        if(taskStatusEnum == TaskStatusEnum.CANCEL){
            return  TaskStatusEnum.CANCEL;
        }
        AbsTaskRunner   absTaskRunner = queueTasks.get(index);
        if(null != absTaskRunner && absTaskRunner.taskStatusEnum != TaskStatusEnum.CANCEL){
            TaskStatusEnum  temp = absTaskRunner.doBackground();
            if(temp == TaskStatusEnum.SUCCESS ){
                index++;
                doSonTaskBackground();
            }else{
                return  temp;
            }
        }
        return  TaskStatusEnum.SUCCESS;
    }
}
