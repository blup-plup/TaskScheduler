package com.Observe.TaskScheduler.Repositories;

import com.Observe.TaskScheduler.Entities.TASK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TASKRepository extends CrudRepository<TASK,Long> {

    List<TASK> findByStatusAndPriority(String status, int priority);

    List<TASK> findByStatus(String status);

}
