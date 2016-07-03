package com.akingyin.sharelibs.taskManager;

import com.akingyin.sharelibs.taskManager.enums.TaskStatusEnum;

/**
 * 任务执行结果回调
 * Created by Administrator on 2016/7/3.
 */
public interface ITaskResultCallBack {

    void    onCallBack(TaskStatusEnum   statusEnum,String  error);

}
