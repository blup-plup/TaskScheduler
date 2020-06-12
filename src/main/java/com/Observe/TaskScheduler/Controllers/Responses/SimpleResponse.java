package com.Observe.TaskScheduler.Controllers.Responses;

import lombok.Data;

@Data
public class SimpleResponse {

    private Integer status;
    private String message;
    private Object obj;

    public SimpleResponse(Integer status, String message, Object obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
    }
}
