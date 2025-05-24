package com.lawfirm.analysis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 分析模块基础配置类
 */
@Configuration
@EnableScheduling
@EnableTransactionManagement
public class AnalysisConfig {

    /**
     * 方法参数验证处理器
     * Bean名称已指定，避免与Spring Boot内置Bean冲突
     */
    @Bean("analysisMethodValidator")
    @ConditionalOnMissingBean(MethodValidationPostProcessor.class)
    public MethodValidationPostProcessor analysisMethodValidator() {
        return new MethodValidationPostProcessor();
    }

    /**
     * 分析编号生成器
     */
    @Bean(name = "analysisNoGenerator")
    public AnalysisNoGenerator analysisNoGenerator() {
        return new AnalysisNoGenerator();
    }

    /**
     * 分析编号生成器
     */
    public static class AnalysisNoGenerator {
        private static final String PREFIX = "AN";
        public String generate() {
            // 前缀+时间戳+6位随机数
            return PREFIX + System.currentTimeMillis() % 1000000000;
        }
    }
} 