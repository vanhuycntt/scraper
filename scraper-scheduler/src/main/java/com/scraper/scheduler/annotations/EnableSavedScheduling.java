package com.scraper.scheduler.annotations;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SchedulingPersistentConfiguration.class)
@Documented
public @interface EnableSavedScheduling {
}
