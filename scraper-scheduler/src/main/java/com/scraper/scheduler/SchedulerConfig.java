package com.scraper.scheduler;

import com.scraper.scheduler.annotations.EnableSavedScheduling;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.stream.IntStream;

@Configuration
@EnableSavedScheduling
public class SchedulerConfig {
    @Bean()
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(15);
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-task");
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        return threadPoolTaskScheduler;
    }
    @Scheduled(initialDelay = 2000L, fixedRate = 15 * 60 * 1_000L)
    @Scheduled(initialDelay = 2000L, fixedDelay = 60 * 1_000L)
    public void reviewTask() {
        IntStream.range(0, 10).forEach(System.out::println);
    }

}
