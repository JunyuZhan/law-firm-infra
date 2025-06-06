package com.lawfirm.personnel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * 人事模块配置类
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "law-firm.personnel")
public class PersonnelConfig {
    
    /**
     * 员工编号配置
     */
    private EmployeeCodeConfig employeeCode = new EmployeeCodeConfig();
    
    /**
     * 文件上传配置
     */
    private UploadConfig upload = new UploadConfig();
    
    /**
     * 邮件通知配置
     */
    private NotificationConfig notification = new NotificationConfig();
    
    /**
     * 员工编号配置类
     */
    @Data
    public static class EmployeeCodeConfig {
        /**
         * 员工编号前缀
         */
        private String prefix = "EMP";
        
        /**
         * 员工编号长度
         */
        private int length = 8;
        
        /**
         * 是否包含日期
         */
        private boolean includeDate = true;
    }
    
    /**
     * 文件上传配置类
     */
    @Data
    public static class UploadConfig {
        /**
         * 员工照片上传路径
         */
        private String photoPath = "/data/upload/personnel/photos";
        
        /**
         * 文档上传路径
         */
        private String docPath = "/data/upload/personnel/docs";
        
        /**
         * 允许的文件类型
         */
        private String allowedTypes = "jpg,jpeg,png,pdf,doc,docx,xls,xlsx";
        
        /**
         * 最大文件大小（MB）
         */
        private int maxSize = 10;
    }
    
    /**
     * 邮件通知配置类
     */
    @Data
    public static class NotificationConfig {
        /**
         * 是否启用邮件通知
         */
        private boolean enabled = true;
        
        /**
         * 发件人
         */
        private String sender = "personnel@lawfirm.com";
        
        /**
         * 抄送人
         */
        private String cc = "hr@lawfirm.com";
        
        /**
         * 合同到期提醒天数
         */
        private int contractExpiryDays = 30;
        
        /**
         * 生日提醒天数
         */
        private int birthdayReminderDays = 7;
    }
} 