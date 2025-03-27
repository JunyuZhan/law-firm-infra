package com.lawfirm.api.config;

import com.lawfirm.document.config.aspect.DocumentLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档模块配置类
 * 负责配置文档模块相关Bean
 */
@Configuration
public class DocumentConfig {

    /**
     * 禁用文档日志切面
     * 避免因LogProperties注入问题导致应用启动失败
     */
    /*
    @Bean
    public DocumentLogAspect documentLogAspect(LogProperties logProperties) {
        return new DocumentLogAspect(logProperties);
    }
    */
} 