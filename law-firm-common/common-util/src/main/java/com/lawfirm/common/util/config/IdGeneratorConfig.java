package com.lawfirm.common.util.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ID生成器配置类
 * 
 * 提供各种ID生成策略的配置
 */
@Configuration("commonIdGeneratorConfig")
public class IdGeneratorConfig {

    /**
     * 雪花算法ID生成器
     * 
     * @return 雪花算法ID生成器
     */
    @Bean(name = "commonSnowflakeIdGenerator")
    @ConditionalOnMissingBean(name = "snowflakeIdGenerator")
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(1, 1);
    }
    
    /**
     * UUID生成器
     * 
     * @return UUID生成器
     */
    @Bean(name = "commonUuidGenerator")
    @ConditionalOnMissingBean(name = "uuidGenerator")
    public UuidGenerator uuidGenerator() {
        return new UuidGenerator();
    }
    
    /**
     * 雪花算法ID生成器内部类
     */
    public static class SnowflakeIdGenerator {
        private final long workerId;
        private final long datacenterId;
        
        public SnowflakeIdGenerator(long workerId, long datacenterId) {
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }
        
        public long nextId() {
            // 简化的实现，实际项目中应使用完整的雪花算法
            return System.currentTimeMillis();
        }
    }
    
    /**
     * UUID生成器内部类
     */
    public static class UuidGenerator {
        public String nextId() {
            return java.util.UUID.randomUUID().toString();
        }
        
        public String nextIdWithoutDash() {
            return java.util.UUID.randomUUID().toString().replace("-", "");
        }
    }
} 