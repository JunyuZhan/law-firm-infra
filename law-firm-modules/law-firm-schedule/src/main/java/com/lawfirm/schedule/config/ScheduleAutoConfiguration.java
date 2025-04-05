package com.lawfirm.schedule.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 日程模块自动配置类
 */
@AutoConfiguration
@ComponentScan("com.lawfirm.schedule")
@Import({
    ScheduleConfig.class,
    ScheduleTaskConfig.class,
    ScheduleAsyncConfig.class
})
public class ScheduleAutoConfiguration {
    // 通过导入完成自动配置
} 