package com.Observe.TaskScheduler.Controllers;

import com.Observe.TaskScheduler.Controllers.Requests.TASKRequest;
import org.springframework.http.ResponseEntity;

public interface TASKController {

    ResponseEntity addTask(TASKRequest request);

    ResponseEntity listAllActiveTasks();
}
