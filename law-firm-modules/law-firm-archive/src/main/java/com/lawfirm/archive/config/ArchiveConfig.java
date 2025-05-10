package com.lawfirm.archive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 档案管理配置类
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ArchiveConfig implements WebMvcConfigurer {
    
    /**
     * 档案模块配置Bean
     */
    @Bean("archiveConfigBean")
    public Object archiveConfigBean() {
        return new ArchiveConfigWrapper("archive-config");
    }
    
    /**
     * 档案配置包装类
     */
    public static class ArchiveConfigWrapper {
        private final String configValue;
        
        public ArchiveConfigWrapper(String configValue) {
            this.configValue = configValue;
        }
        
        public String getConfigValue() {
            return configValue;
        }
    }
} 