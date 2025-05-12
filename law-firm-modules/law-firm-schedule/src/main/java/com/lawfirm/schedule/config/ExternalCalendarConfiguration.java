package com.lawfirm.schedule.config;

import com.lawfirm.model.schedule.service.ExternalCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 外部日历配置类
 * 提供外部日历服务相关配置
 */
@Slf4j
@Configuration
public class ExternalCalendarConfiguration {
    
    /**
     * 配置相关Bean
     */
    @Bean
    public String externalCalendarConfigInfo() {
        log.info("初始化外部日历配置");
        return "外部日历配置已初始化";
    }
} 