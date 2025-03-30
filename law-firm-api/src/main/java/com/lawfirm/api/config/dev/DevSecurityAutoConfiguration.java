package com.lawfirm.api.config.dev;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * 开发环境安全自动配置类
 * <p>
 * 统一导入所有开发环境相关的安全配置
 * </p>
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "dev.auth.simplified-security", havingValue = "true")
@Import({
    DevAuthConfig.class,
    DevSecurityContextConfig.class,
    DevSecurityFilterConfig.class
})
@Order(60)  // 高优先级，确保在其他安全配置之前执行
public class DevSecurityAutoConfiguration {
    
    public DevSecurityAutoConfiguration() {
        log.info("初始化开发环境安全自动配置");
    }
}