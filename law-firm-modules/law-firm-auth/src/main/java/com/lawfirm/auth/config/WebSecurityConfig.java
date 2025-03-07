package com.lawfirm.auth.config;

import com.lawfirm.common.security.config.BaseWebSecurityConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块Web安全配置
 * 继承自common-security模块的基础配置
 */
@Configuration
public class WebSecurityConfig extends BaseWebSecurityConfig {
    // 保留auth模块特有的Web安全配置
}