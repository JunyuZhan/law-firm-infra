package com.lawfirm.document.config;

import com.lawfirm.common.log.properties.LogProperties;
import com.lawfirm.document.config.aspect.DocumentLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * 文档日志配置类
 * <p>
 * 负责配置文档模块相关日志Bean
 * </p>
 */
@Slf4j
@Configuration
public class DocumentLogConfig {

    /**
     * 创建文档日志切面
     * 
     * @param logProperties 日志属性配置
     * @return 文档日志切面实例
     */
    @Bean
    @ConditionalOnProperty(name = "law.firm.log.enabled", havingValue = "true", matchIfMissing = true)
    public DocumentLogAspect documentLogAspect(LogProperties logProperties) {
        log.info("初始化文档模块日志切面");
        return new DocumentLogAspect(logProperties);
    }
} 