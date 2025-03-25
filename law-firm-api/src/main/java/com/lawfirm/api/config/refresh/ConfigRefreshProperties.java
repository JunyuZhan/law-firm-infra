package com.lawfirm.api.config.refresh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "config.refresh")
public class ConfigRefreshProperties {
    /**
     * 是否启用配置刷新
     */
    private boolean enabled = true;
    
    /**
     * 刷新间隔（毫秒）
     */
    private long refreshInterval = 30000;
    
    /**
     * 是否在启动时刷新
     */
    private boolean refreshOnStartup = true;
} 