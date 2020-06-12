package com.Observe.TaskScheduler.Controllers.Impl;

import com.Observe.TaskScheduler.Biz.TASKBiz;
import com.Observe.TaskScheduler.Common.Constants;
import com.Observe.TaskScheduler.Controllers.Requests.TASKRequest;
import com.Observe.TaskScheduler.Controllers.Responses.SimpleResponse;
import com.Observe.TaskScheduler.Controllers.TASKController;
import com.Observe.TaskScheduler.Entities.TASK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/task")
@CrossOrigin
public class TASKControllerImpl implements TASKController {

    @Autowired
    TASKBiz taskBiz;

    @Override
    @PostMapping(path = "/add")
    public ResponseEntity addTask(@RequestBody TASKRequest request) {
        TASK task = null;
        try{
            task = taskBiz.addTask(request);
        } catch(Exception e){
            return ResponseEntity.ok().body(new SimpleResponse(400,e.getMessage(),null));
        }
        return ResponseEntity.ok().body(new SimpleResponse(200, Constants.successResponse,task));
    }

    @Override
    @GetMapping(path = "/list")
    public ResponseEntity listAllActiveTasks() {
        List<TASK> taskList = taskBiz.viewAllActiveTasks();

        return ResponseEntity.ok().body(new SimpleResponse(200,Constants.successResponse,taskList));
    }
}
