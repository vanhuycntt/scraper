package com.scraper.scheduler.annotations;


import org.springframework.scheduling.config.Task;

import java.util.Set;

public interface SchedulingTaskHolder {
    /**
     *
     * @return
     */
    Set<Task> getDeclaredTasks();
}
