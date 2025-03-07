package com.lawfirm.auth.config;

import com.lawfirm.common.web.config.CorsConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块 跨域配置
 * 继承自common-web模块的基础配置
 */
@Configuration
public class AuthCorsConfig extends CorsConfig {
    // 父类已包含基础配置
}