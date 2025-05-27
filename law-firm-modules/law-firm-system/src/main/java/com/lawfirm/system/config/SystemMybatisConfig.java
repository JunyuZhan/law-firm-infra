package com.lawfirm.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统模块MyBatis配置类
 * 使用默认的MyBatis-Plus配置，支持注解和BaseMapper
 */
@Configuration("systemMybatisConfig")
@MapperScan(basePackages = "com.lawfirm.model.system.mapper")
public class SystemMybatisConfig {
    // 使用默认的MyBatis-Plus配置，避免自定义SqlSessionFactory导致的问题
} 