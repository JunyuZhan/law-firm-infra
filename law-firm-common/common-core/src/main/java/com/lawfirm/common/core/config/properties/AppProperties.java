package com.lawfirm.common.core.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 应用配置属性
 * 提供应用级别的配置信息
 */
@Data
@Validated
@ConfigurationProperties(prefix = "law-firm.common.app")
public class AppProperties {
    
    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String name = "律师事务所管理系统";
    
    /**
     * 应用版本
     */
    private String version = "1.0.0";
    
    /**
     * 应用描述
     */
    private String description = "为律师事务所提供全方位的信息化管理平台";
    
    /**
     * 是否开发环境
     */
    private boolean dev = false;
    
    /**
     * 应用域名
     */
    private String domain;
    
    /**
     * 组织信息
     */
    private Organization organization = new Organization();
    
    /**
     * 系统管理员信息
     */
    private Admin admin = new Admin();
    
    /**
     * 组织信息
     */
    @Data
    public static class Organization {
        /**
         * 组织名称
         */
        private String name = "律师事务所";
        
        /**
         * 组织代码
         */
        private String code;
    }
    
    /**
     * 管理员信息
     */
    @Data
    public static class Admin {
        /**
         * 管理员账号
         */
        private String username = "admin";
    }
} 