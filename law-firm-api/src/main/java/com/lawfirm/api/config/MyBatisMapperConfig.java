package com.lawfirm.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 统一的MyBatis Mapper扫描配置
 */
@Configuration
@MapperScan(basePackages = "com.lawfirm.model.**.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisMapperConfig {
    // 配置类，无需方法
} 