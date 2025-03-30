package com.lawfirm.document.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 文档模块自动配置类
 */
@AutoConfiguration
@ComponentScan("com.lawfirm.document")
@Import({
    DocumentLogConfig.class,
    DocumentSearchConfig.class,
    StorageFallbackConfig.class,
    DocumentSecurityConfig.class
})
public class DocumentAutoConfiguration {
    // 通过导入完成自动配置
} 