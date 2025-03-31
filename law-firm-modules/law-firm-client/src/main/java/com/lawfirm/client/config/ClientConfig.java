package com.lawfirm.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
     * 
     * 注意：Bean名称已修改为clientMethodValidator以避免与Spring Boot内置Bean冲突
     * 添加条件注解，防止与ValidationAutoConfiguration冲突
     */
    @Bean("clientMethodValidator")
    @ConditionalOnMissingBean(MethodValidationPostProcessor.class)
    public MethodValidationPostProcessor clientMethodValidator() {
        return new MethodValidationPostProcessor();
    }
    
    /**
     * 客户编号生成器
     */
    @Bean(name = "clientNoGenerator")
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
