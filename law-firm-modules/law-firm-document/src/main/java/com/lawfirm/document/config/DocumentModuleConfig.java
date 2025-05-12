package com.lawfirm.document.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 文档模块配置类
 * 负责扫描文档模块相关的Mapper接口
 */
@Configuration
@EnableCaching
@MapperScan(basePackages = {"com.lawfirm.model.document.mapper"})
public class DocumentModuleConfig {
    // 文档模块配置
} 