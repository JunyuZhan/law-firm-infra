package com.lawfirm.api.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;
import com.lawfirm.system.config.SystemAutoConfiguration;

/**
 * API模块引导配置类
 * <p>
 * 显式导入关键配置类，解决自动配置导入机制可能的问题
 * </p>
 */
@AutoConfiguration
@ImportAutoConfiguration
@Import({
    DdlConfig.class,
    ApiMyBatisConfig.class,
    SystemAutoConfiguration.class
})
public class ApiBootConfiguration {
    // 此类用于确保关键配置类被正确加载
} 