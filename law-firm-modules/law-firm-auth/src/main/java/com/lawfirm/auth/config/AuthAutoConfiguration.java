package com.lawfirm.auth.config;

import com.lawfirm.common.core.config.BaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块自动配置
 * 继承自common-core模块的基础自动配置
 */
@Configuration
public class AuthAutoConfiguration extends BaseAutoConfiguration {
    // 保留auth模块特有的自动配置
}