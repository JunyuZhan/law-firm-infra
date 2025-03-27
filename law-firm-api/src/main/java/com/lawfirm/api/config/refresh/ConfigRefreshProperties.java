package com.lawfirm.api.config.refresh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置刷新属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "config.refresh")
public class ConfigRefreshProperties {
    
    /**
     * 是否启用配置自动刷新
     */
    private boolean enabled = false;
    
    /**
     * 刷新间隔（毫秒）
     */
    private long refreshInterval = 30000;
    
    /**
     * 是否在应用启动时刷新配置
     */
    private boolean refreshOnStartup = true;
    
    /**
     * 刷新源类型（file, db, remote）
     */
    private String sourceType = "file";
    
    /**
     * 配置源路径
     */
    private String sourcePath = "";
} 