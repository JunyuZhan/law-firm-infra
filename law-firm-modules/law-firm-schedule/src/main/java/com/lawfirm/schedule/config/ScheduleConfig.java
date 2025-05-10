package com.lawfirm.schedule.config;

import com.lawfirm.schedule.config.properties.ScheduleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * 日程模块配置类
 */
@Configuration
@Slf4j
public class ScheduleConfig {

    /**
     * 初始化日程配置
     */
    @Bean(name = "scheduleModuleInitializer")
    @ConditionalOnProperty(name = "lawfirm.schedule.enabled", havingValue = "true", matchIfMissing = true)
    public Object scheduleModuleInitializer(ScheduleProperties scheduleProperties) {
        log.info("日程管理模块初始化完成");
        log.info("日程提醒功能: {}", scheduleProperties.getReminder().isEnabled() ? "已启用" : "已禁用");
        log.info("日程冲突检测: {}", scheduleProperties.getConflict().isEnabled() ? "已启用" : "已禁用");
        log.info("日程同步功能: {}", scheduleProperties.getSync().isEnabled() ? "已启用" : "已禁用");
        log.info("日程重复规则: {}", scheduleProperties.getRecurrence().isEnabled() ? "已启用" : "已禁用");
        
        return new ScheduleModuleWrapper("scheduleModuleInitializer", scheduleProperties);
    }

    /**
     * 配置日程处理器
     */
    @Bean(name = "scheduleProcessor")
    @ConditionalOnProperty(name = "lawfirm.schedule.enabled", havingValue = "true", matchIfMissing = true)
    public ScheduleProcessor scheduleProcessor() {
        return new ScheduleProcessor();
    }

    /**
     * 日程处理器
     * 负责处理模块初始化后的业务逻辑
     */
    public static class ScheduleProcessor {
        
        /**
         * 处理模块初始化任务
         */
        public void process() {
            // 模块初始化后的业务处理逻辑
        }
    }
    
    /**
     * 日程模块包装类
     */
    public static class ScheduleModuleWrapper {
        private final String name;
        private final ScheduleProperties properties;
        
        public ScheduleModuleWrapper(String name, ScheduleProperties properties) {
            this.name = name;
            this.properties = properties;
        }
        
        public String getName() {
            return name;
        }
        
        public ScheduleProperties getProperties() {
            return properties;
        }
    }
} 