package com.Observe.TaskScheduler.Repositories;

import com.Observe.TaskScheduler.Entities.CurrentTASK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrentTASKRepository extends CrudRepository<CurrentTASK,Long> {

    List<CurrentTASK> findAll();

}
