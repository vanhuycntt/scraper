package com.scraper.app.task;

import com.scraper.app.producer.avro.Course;
import com.scraper.app.producer.avro.Review;
import com.scraper.app.producer.avro.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
@EnableScheduling
public class SchedulerTaskConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTaskConfig.class);

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
        LOGGER.info("start producing reviews");

        IntStream.range(0, 100).mapToObj(this::jsonToReview).forEach(rv-> {
            /*try {
                //kafkaTemplate.send("udemy-reviews", rv).get();
            } catch (InterruptedException | ExecutionException ex) {
                LOGGER.error("Failure to produce the review" + rv.getId(), ex);
            }*/
        });
        LOGGER.info("completed producing reviews");
    }

    @Bean
    public SchedulingConfigurer generateReportTask() {
        return new SchedulingConfigurer() {
            @Override
            public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
                taskRegistrar.addTriggerTask(() -> {
                    System.out.print("task configurer");
                }, (context)-> null);

                taskRegistrar.getTriggerTaskList().forEach(triggerTask -> {
                    taskRegistrar.scheduleTriggerTask(triggerTask);
                });
            }
        };
    }

    public Review jsonToReview(int idx) {
        Review.Builder reviewBuilder = Review.newBuilder();
        reviewBuilder.setContent("content" + idx);
        reviewBuilder.setId(ThreadLocalRandom.current().nextInt());
        reviewBuilder.setRating("2");
        reviewBuilder.setTitle("default books");
        reviewBuilder.setCreated(DateTime.now());
        reviewBuilder.setModified(DateTime.now());
        reviewBuilder.setUser(jsonToUser(idx));
        reviewBuilder.setCourse(jsonToCourse(idx));

        return reviewBuilder.build();
    }
    public User jsonToUser(int idx) {
        User.Builder userBuilder = User.newBuilder();
        userBuilder.setTitle("title_" + idx);
        userBuilder.setName("name_" + idx);
        userBuilder.setDisplayName("display_" + idx);
        return userBuilder.build();
    }

    public Course jsonToCourse(int idx) {
        Course.Builder courseBuilder = Course.newBuilder();
        courseBuilder.setId(ThreadLocalRandom.current().nextInt());
        courseBuilder.setTitle("title_" + idx);
        courseBuilder.setUrl("url_" + idx);

        return courseBuilder.build();
    }

}
