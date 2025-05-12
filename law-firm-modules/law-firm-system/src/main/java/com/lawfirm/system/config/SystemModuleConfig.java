package com.lawfirm.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统管理模块配置类
 */
@Configuration
@EnableCaching
@MapperScan(basePackages = {"com.lawfirm.model.system.mapper"})
public class SystemModuleConfig {

    /**
     * 系统安全配置
     */
    @Bean("systemSecurityProperties")
    @ConfigurationProperties(prefix = "system.security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    /**
     * 系统监控配置
     */
    @Bean(name = "systemMonitorProperties")
    @ConfigurationProperties(prefix = "system.monitor")
    public MonitorProperties monitorProperties() {
        return new MonitorProperties();
    }

    /**
     * 系统升级配置
     */
    @Bean(name = "systemUpgradeProperties")
    @ConfigurationProperties(prefix = "system.upgrade")
    public UpgradeProperties upgradeProperties() {
        return new UpgradeProperties();
    }

    /**
     * 系统日志配置
     * 
     * 注意：Bean名称已更改为systemLogProperties，避免与common-log模块冲突
     */
    @Bean("systemLogProperties")
    @ConfigurationProperties(prefix = "system.log")
    public LogProperties systemLogProperties() {
        return new LogProperties();
    }

    /**
     * 安全配置属性
     */
    @Data
    public static class SecurityProperties {
        /**
         * 管理员角色
         */
        private String adminRole = "ROLE_ADMIN";
        
        /**
         * IP白名单，多个IP用逗号分隔
         */
        private String ipWhitelist;
    }

    /**
     * 监控配置属性
     */
    @Data
    public static class MonitorProperties {
        /**
         * 指标收集间隔（秒）
         */
        private int metricsInterval = 60;
        
        /**
         * 告警阈值
         */
        @Getter
        @Setter
        private AlertThreshold alertThreshold = new AlertThreshold();
        
        /**
         * 告警阈值配置
         */
        @Data
        public static class AlertThreshold {
            /**
             * CPU使用率阈值（百分比）
             */
            private int cpu = 80;
            
            /**
             * 内存使用率阈值（百分比）
             */
            private int memory = 80;
            
            /**
             * 磁盘使用率阈值（百分比）
             */
            private int disk = 90;
        }
    }

    /**
     * 升级配置属性
     */
    @Data
    public static class UpgradeProperties {
        /**
         * 备份路径
         */
        private String backupPath;
        
        /**
         * 补丁路径
         */
        private String patchPath;
    }

    /**
     * 日志配置属性
     */
    @Data
    public static class LogProperties {
        /**
         * 日志保留天数
         */
        private int retentionDays = 30;
        
        /**
         * 是否启用审计
         */
        private boolean auditEnabled = true;
        
        /**
         * 日志导出路径
         */
        private String exportPath;
    }
} 