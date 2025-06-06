package com.lawfirm.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 日程任务调度配置
 */
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTaskConfig {
    
    /**
     * 线程池任务调度器 - 作为系统主要的TaskScheduler
     */
    @Bean(name = "scheduleTaskScheduler")
    @Primary  // 设置为主要的TaskScheduler，解决Bean冲突
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("初始化日程任务调度器（Primary TaskScheduler）");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("schedule-task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(throwable -> log.error("日程任务执行异常", throwable));
        return scheduler;
    }
} 