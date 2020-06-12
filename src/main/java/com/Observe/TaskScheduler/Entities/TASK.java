package com.Observe.TaskScheduler.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TASK {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    int priority;
    String durationInSecs;
    String status;
    long executionTime;
    String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDurationInSecs() {
        return durationInSecs;
    }

    public void setDurationInSecs(String durationInSecs) {
        this.durationInSecs = durationInSecs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
