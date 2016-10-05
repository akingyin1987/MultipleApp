package com.md.multipleapp;

import com.akingyin.sharelibs.jlog.JLog;
import com.akingyin.sharelibs.taskManager.AbsTaskRunner;
import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;
import java.util.Random;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/10/5 10:49
 * @ Version V1.0
 */

public class TestTask  extends AbsTaskRunner {



  public TestTask() {
    for(int i=0;i<10;i++){
      TestSonTask   task = new TestSonTask();
      addTask(task);
    }
  }

  @Override public TaskStatusEnum onBefore() {
    JLog.d("TestTask-onBefore"+getTag());
    return TaskStatusEnum.SUCCESS;
  }

  @Override public TaskStatusEnum onToDo() {
    JLog.d("TestTask-onBefore"+getTag());
    try {
      Thread.sleep(new Random().nextInt(1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int  flag = new Random().nextInt(7);
    JLog.d("flag="+flag);
    if(flag==0){
      return TaskStatusEnum.SUCCESS;
    }
    return TaskStatusEnum.getName(flag);

  }
}
