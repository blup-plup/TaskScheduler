package com.Observe.TaskScheduler;

import com.Observe.TaskScheduler.Biz.TASKBiz;
import com.Observe.TaskScheduler.Common.Constants;
import com.Observe.TaskScheduler.Entities.CurrentTASK;
import com.Observe.TaskScheduler.Entities.TASK;
import com.Observe.TaskScheduler.Repositories.CurrentTASKRepository;
import com.Observe.TaskScheduler.Repositories.TASKRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class TASKExecutor {

    @Autowired
    TASKRepository taskRepository;

    @Autowired
    TASKBiz taskBiz;

    @Autowired
    CurrentTASKRepository currentTASKRepository;

    private static final Logger log = LoggerFactory.getLogger(TASKExecutor.class);




    @Scheduled(cron = "*/1 * * * * *")
    @Async
    public void executionService() throws InterruptedException {
        log.info("Running Task Execution service");

        TASK highTask = taskBiz.getHighTask();
        TASK mediumTask = taskBiz.getMediumTask();
        TASK lowTask = taskBiz.getLowTask();


        if(highTask != null){
            bookthread(highTask);
        }

        if(mediumTask != null){
            bookthread(mediumTask);
        }

        if(lowTask != null){
            bookthread(lowTask);
        }

    }




    private void bookthread(TASK task) throws InterruptedException {
        List<CurrentTASK> currentTASKList = currentTASKRepository.findAll();

        if(currentTASKList == null || currentTASKList.size() < 2){
            CurrentTASK currentTASK = new CurrentTASK();
            currentTASK.setParentId(task.getId());
            currentTASK.setDurationInSecs(task.getDurationInSecs());
            currentTASK.setPriority(task.getPriority());
            currentTASK.setType(task.getType());
            currentTASK.setEndTime(task.getExecutionTime()+Long.valueOf(task.getDurationInSecs()));

            log.info("starting execution of task with id: {}",task.getId());
            task.setStatus(Constants.statusMap.get(3));
            taskRepository.save(task);

            currentTASKRepository.save(currentTASK);
            return;

        }
        CurrentTASK currentTASK = currentTASKList.get(0);
        CurrentTASK currentTASK1 = currentTASKList.get(1);

        if(currentTASK.getPriority() < currentTASK1.getPriority()){
            currentTASK = currentTASK1;
        }

        if(currentTASK.getPriority() > task.getPriority()){
            log.info("stopping execution of task with id {} and starting execution of task with id: {}",currentTASK.getParentId(),task.getId());
            task.setStatus(Constants.statusMap.get(3));
            taskRepository.save(task);
            taskBiz.postProcess(currentTASK.getParentId(),2);
            currentTASK.setParentId(task.getId());
            currentTASK.setDurationInSecs(task.getDurationInSecs());
            currentTASK.setPriority(task.getPriority());
            currentTASK.setType(task.getType());
            currentTASK.setEndTime(task.getExecutionTime()+Long.valueOf(task.getDurationInSecs()));

            currentTASKRepository.save(currentTASK);
            return;
        }

        log.info("marking task id: {} as failure cause higher priority task already executing",task.getId());
        taskBiz.postProcess(task.getId(),2);
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void taskStatusUpdate(){

        List<CurrentTASK> currentTASKList = currentTASKRepository.findAll();

        long time = System.currentTimeMillis()/1000L;

        for(CurrentTASK currentTASK: currentTASKList){
            log.info("task id: {} ending in {}",currentTASK.getParentId(),currentTASK.getEndTime() - System.currentTimeMillis()/1000L);
        }


        for(CurrentTASK currentTASK: currentTASKList){
            if(time >= currentTASK.getEndTime()){
                currentTASKRepository.delete(currentTASK);
                log.info("task with id {} successfully completed", currentTASK.getParentId());
                taskBiz.postProcess(currentTASK.getParentId(),1);
            }
        }
    }
}
