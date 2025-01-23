package com.lawfirm.core.workflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 工作流配置属性
 */
@Data
@ConfigurationProperties(prefix = "workflow")
public class WorkflowProperties {
    
    /**
     * 流程定义配置
     */
    private ProcessConfig process;
    
    /**
     * 任务配置
     */
    private TaskConfig task;
    
    @Data
    public static class ProcessConfig {
        /**
         * 流程定义文件路径
         */
        private String definitionLocation;
        
        /**
         * 是否自动部署流程定义
         */
        private Boolean autoDeployment;
        
        /**
         * 是否检查流程定义版本
         */
        private boolean checkVersion = true;
        
        /**
         * 流程历史记录保留天数（0表示永久保留）
         */
        private int historyRetentionDays = 0;
    }
    
    @Data
    public static class TaskConfig {
        /**
         * 任务超时时间（小时）
         */
        private Long timeout;
        
        /**
         * 任务提醒时间（小时）
         */
        private int reminder = 24;
        
        /**
         * 任务自动完成
         */
        private boolean autoComplete = false;
        
        /**
         * 任务自动认领
         */
        private boolean autoClaim = false;
        
        /**
         * 任务锁定时间（分钟）
         */
        private Long lockTime;
    }
} 