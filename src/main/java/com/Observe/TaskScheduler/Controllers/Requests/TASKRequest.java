package com.Observe.TaskScheduler.Controllers.Requests;

import lombok.Data;

@Data
public class TASKRequest {
    String priority;
    String durationInSecs;
    String executionTime;
    String type;
}
