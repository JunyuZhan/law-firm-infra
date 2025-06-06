package com.lawfirm.auth.config;

import org.springframework.context.annotation.Configuration;

/**
 * Auth模块MyBatis-Plus配置类
 * 注意：Mapper扫描已在主应用LawFirmApiApplication中统一配置
 * 此处不再重复配置，避免Bean重复定义警告
 */
@Configuration("authMybatisConfig")
public class AuthMybatisConfig {
    // Mapper扫描由主应用统一处理，避免重复定义
} 