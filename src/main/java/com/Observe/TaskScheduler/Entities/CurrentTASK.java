package com.Observe.TaskScheduler.Entities;

import javax.persistence.*;

@Entity
public class CurrentTASK {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long ParentId;
    Integer priority;
    String durationInSecs;
    String type;
    Long endTime;

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return ParentId;
    }

    public void setParentId(Long parentId) {
        ParentId = parentId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDurationInSecs() {
        return durationInSecs;
    }

    public void setDurationInSecs(String durationInSecs) {
        this.durationInSecs = durationInSecs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
