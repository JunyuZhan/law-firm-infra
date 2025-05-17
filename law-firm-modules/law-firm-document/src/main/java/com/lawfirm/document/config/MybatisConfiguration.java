package com.lawfirm.document.config;

import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * 确保所有Mapper都被正确扫描
 */
@Configuration("documentMybatisConfiguration")
public class MybatisConfiguration {
    // 只添加扫描注解，其他配置留给全局配置文件
} 