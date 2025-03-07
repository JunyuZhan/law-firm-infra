package com.lawfirm.auth.config;

import com.lawfirm.common.data.config.MybatisPlusConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块 MyBatis-Plus 配置
 * 继承自common-data模块的基础配置
 */
@Configuration
public class AuthMybatisPlusConfig extends MybatisPlusConfig {
    // 父类已包含基础配置
}