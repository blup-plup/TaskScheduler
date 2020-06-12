package com.Observe.TaskScheduler.Biz;

import com.Observe.TaskScheduler.Controllers.Requests.TASKRequest;
import com.Observe.TaskScheduler.Entities.TASK;

import java.util.List;

public interface TASKBiz {

    TASK addTask(TASKRequest taskRequest) throws Exception;

    TASK getHighTask();

    TASK getMediumTask();

    TASK getLowTask();

    void postProcess(Long taskId,Integer status);

    List<TASK> viewAllActiveTasks();


}
