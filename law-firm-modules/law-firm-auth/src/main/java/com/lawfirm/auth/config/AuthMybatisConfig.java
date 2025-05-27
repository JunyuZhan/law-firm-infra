package com.lawfirm.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Auth模块MyBatis-Plus配置类
 * 使用注解方式，无需XML映射文件
 */
@Configuration("authMybatisConfig")
@MapperScan(basePackages = "com.lawfirm.model.auth.mapper")
public class AuthMybatisConfig {
    // 使用默认的MyBatis-Plus配置，支持注解和BaseMapper
} 