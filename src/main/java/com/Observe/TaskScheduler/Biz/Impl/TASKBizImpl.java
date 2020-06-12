package com.Observe.TaskScheduler.Biz.Impl;

import com.Observe.TaskScheduler.Biz.TASKBiz;
import com.Observe.TaskScheduler.Common.CommonUtils;
import com.Observe.TaskScheduler.Common.Constants;
import com.Observe.TaskScheduler.Controllers.Requests.TASKRequest;
import com.Observe.TaskScheduler.Entities.TASK;
import com.Observe.TaskScheduler.Repositories.TASKRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TASKBizImpl implements TASKBiz {

    @Autowired
    TASKRepository taskRepository;

    private static final Logger log = LoggerFactory.getLogger(TASKBizImpl.class);

    @Override
    public TASK addTask(TASKRequest taskRequest) throws Exception {
        validateTaskRequest(taskRequest);
        TASK task = new TASK();
        task.setDurationInSecs(taskRequest.getDurationInSecs());
        task.setType(taskRequest.getType());
        task.setStatus("active");
        long unixTimeNow = System.currentTimeMillis()/1000L;
        if(CommonUtils.isOneTimeExecution(task.getType())){
            long executionTime = unixTimeNow + Integer.valueOf(taskRequest.getExecutionTime())*60;
            task.setExecutionTime(executionTime);
        }

        if(taskRequest.getPriority().equalsIgnoreCase("high")){
            task.setPriority(1);
        }
        if(taskRequest.getPriority().equalsIgnoreCase("low")){
            task.setPriority(3);
        }
        if(taskRequest.getPriority().equalsIgnoreCase("medium")){
            task.setPriority(2);
        }
        return taskRepository.save(task);
    }



    private void validateTaskRequest(TASKRequest taskRequest) throws Exception {

        if(taskRequest.getPriority() == null || !Constants.prioritySet.contains(taskRequest.getPriority().toLowerCase())){
            throw new Exception("priorities can only be high, low or medium");
        }
        if(taskRequest.getType() == null || !Constants.TaskTypeSet.contains(taskRequest.getType().toLowerCase())){
            throw new Exception("task type can only be A or B");
        }
        if(taskRequest.getDurationInSecs() == null ||
                taskRequest.getDurationInSecs().isEmpty() ||
                !CommonUtils.isNumeric(taskRequest.getDurationInSecs())
                || Integer.valueOf(taskRequest.getDurationInSecs()) <= 0){
            throw new Exception("duration in secs can only be positive numbers");
        }
        if(taskRequest.getExecutionTime() == null || taskRequest.getExecutionTime().isEmpty()){
            throw new Exception("Invalid execution time");
        }
    }

    @Override
    public TASK getHighTask(){

        List<TASK> highPriorityTaskList = taskRepository.findByStatusAndPriority("active",1);
        TASK highTask = null;
        long currentUnixTime = System.currentTimeMillis()/1000L;

        for(int i = 0 ; i < highPriorityTaskList.size(); ++i){
            TASK task = highPriorityTaskList.get(i);
            if(i == 0 || task.getExecutionTime() < highTask.getExecutionTime()){
                highTask = task;
            }
        }
        if(highTask != null && highTask.getExecutionTime() <= currentUnixTime){
            return highTask;
        }

        return null;
    }

    @Override
    public TASK getMediumTask(){
        List<TASK> mediumPriorityTaskList = taskRepository.findByStatusAndPriority("active",2);

        TASK mediumTask = null;

        long currentUnixTime = System.currentTimeMillis()/1000L;

        for(int i = 0 ; i < mediumPriorityTaskList.size(); ++i){
            TASK task = mediumPriorityTaskList.get(i);
            if(i == 0 || task.getExecutionTime() < mediumTask.getExecutionTime()){
                mediumTask = task;
            }
        }
        if(mediumTask != null && mediumTask.getExecutionTime() <= currentUnixTime){
            return mediumTask;
        }

        return null;

    }

    @Override
    public TASK getLowTask(){
        List<TASK> lowPriorityTaskList = taskRepository.findByStatusAndPriority("active",3);

        TASK lowTask = null;

        long currentUnixTime = System.currentTimeMillis()/1000L;

        for(int i = 0 ; i < lowPriorityTaskList.size(); ++i){
            TASK task = lowPriorityTaskList.get(i);
            if(i == 0 || task.getExecutionTime() < lowTask.getExecutionTime()){
                lowTask = task;
            }
        }

        if(lowTask != null && lowTask.getExecutionTime() <= currentUnixTime){
            return lowTask;
        }

        return null;
    }

    @Override
    public void postProcess(Long taskId, Integer status) {
        Optional<TASK> taskOptional = taskRepository.findById(taskId);

        log.info("Post processing task with id {}",taskId);

        if(!taskOptional.isPresent()){
            return;
        }

        TASK task = taskOptional.get();

        task.setStatus(Constants.statusMap.get(status));
        taskRepository.save(task);

    }

    @Override
    public List<TASK> viewAllActiveTasks() {
        List<TASK> taskList = taskRepository.findByStatus("active");

        return taskList;
    }
}
