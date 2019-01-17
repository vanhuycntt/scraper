package com.scraper.app.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class ScheduledTaskManager implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTaskManager.class);

    private String uuid;

    private String name;

    private String description;

    private TaskState state;

    private String expression;

    private TaskOwner owner;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor;

    @Autowired
    private TaskScheduler taskScheduler;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<ScheduledTask> scheduledTasks =  scheduledAnnotationProcessor.getScheduledTasks();
        LOGGER.info("current tasks {}", scheduledTasks.size() );
        /*for(ScheduledTask sch: scheduledTasks) {
            sch.cancel();
            if(sch.getTask() instanceof CronTask) {
                taskScheduler.schedule(sch.getTask().getRunnable(), ((CronTask) sch.getTask()).getTrigger());
            }
            if(sch.getTask() instanceof FixedDelayTask) {

            }

        }*/
    }
}
