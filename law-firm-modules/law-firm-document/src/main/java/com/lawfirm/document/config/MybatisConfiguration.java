package com.lawfirm.document.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * 确保所有Mapper都被正确扫描
 */
@Configuration("documentMybatisConfiguration")
@MapperScan(basePackages = {
    "com.lawfirm.model.**.mapper",
    "com.lawfirm.document.**.mapper",
    "com.lawfirm.system.**.mapper" 
})
public class MybatisConfiguration {
    // 只添加扫描注解，其他配置留给全局配置文件
} 