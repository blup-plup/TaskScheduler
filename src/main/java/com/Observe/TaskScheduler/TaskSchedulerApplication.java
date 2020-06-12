package com.Observe.TaskScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class TaskSchedulerApplication {



	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class, args);
	}

}
