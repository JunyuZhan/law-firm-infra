package com.lawfirm.contract.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.lawfirm.model.contract.service.ContractConflictService;
import com.lawfirm.contract.service.impl.ContractConflictServiceImpl;

import lombok.Data;
import java.util.Arrays;
import java.util.List;

/**
 * 合同冲突检查配置类
 */
@Configuration("contractConflictConfig")
@ConfigurationProperties(prefix = "contract.conflict")
@Data
public class ContractConflictConfig {
    
    /**
     * 是否启用冲突检查
     */
    private boolean enabled = true;
    
    /**
     * 冲突检查时机
     */
    private List<String> checkTiming = Arrays.asList("CREATE", "UPDATE");
    
    /**
     * 冲突级别配置
     */
    private LevelConfig level = new LevelConfig();
    
    /**
     * 冲突检查范围配置
     */
    private ScopeConfig scope = new ScopeConfig();
    
    /**
     * 冲突通知配置
     */
    private NotificationConfig notification = new NotificationConfig();
    
    /**
     * 冲突级别配置内部类
     */
    @Data
    public static class LevelConfig {
        private String client = "MEDIUM";
        private String lawyer = "LOW";
        private String case_ = "HIGH";
    }
    
    /**
     * 冲突检查范围配置内部类
     */
    @Data
    public static class ScopeConfig {
        private int timeRange = 365;
        private boolean includeTerminated = false;
        private boolean includeArchived = false;
    }
    
    /**
     * 冲突通知配置内部类
     */
    @Data
    public static class NotificationConfig {
        private boolean autoNotify = true;
        private List<String> channels = Arrays.asList("EMAIL", "SMS");
        private String template = "发现合同冲突：${conflictDesc}";
    }
    
    /**
     * 配置合同冲突服务
     */
    @Bean("contractConflictServiceConfigBean")
    @Primary
    public ContractConflictService contractConflictService() {
        return new ContractConflictServiceImpl();
    }
} 