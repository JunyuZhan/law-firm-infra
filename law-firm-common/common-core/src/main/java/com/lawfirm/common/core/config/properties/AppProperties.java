package com.lawfirm.common.core.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 应用配置属性
 */
@Data
@Validated
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    
    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String name;
    
    /**
     * 应用版本
     */
    private String version;
    
    /**
     * 应用描述
     */
    private String description;
} 