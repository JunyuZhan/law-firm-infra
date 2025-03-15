package com.lawfirm.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 客户模块基础配置类
 */
@Configuration
@EnableScheduling
@EnableTransactionManagement
public class ClientConfig {
    
    /**
     * 方法参数验证处理器
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    
    /**
     * 客户编号生成器
     */
    @Bean
    public ClientNoGenerator clientNoGenerator() {
        return new ClientNoGenerator();
    }
    
    /**
     * 客户编号生成器
     */
    public static class ClientNoGenerator {
        private static final String PREFIX = "CL";
        
        /**
         * 生成客户编号
         * @return 客户编号
         */
        public String generate() {
            // 前缀+年月日+6位随机数
            return PREFIX + System.currentTimeMillis() % 1000000000;
        }
    }
}
