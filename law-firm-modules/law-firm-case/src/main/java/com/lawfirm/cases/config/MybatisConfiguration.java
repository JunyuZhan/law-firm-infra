package com.lawfirm.cases.config;

import org.springframework.context.annotation.Configuration;

/**
 * 案件模块MyBatis配置
 * 确保所有Mapper都被正确扫描
 */
@Configuration("caseMybatisConfiguration")
// @MapperScan(basePackages = {
//     "com.lawfirm.model.**.mapper",
//     "com.lawfirm.model.**.repository",
//     "com.lawfirm.cases.**.mapper",
//     "com.lawfirm.system.**.mapper",
//     "com.lawfirm.model.system.mapper"
// })  // 建议如需扫描可合并到CaseModuleConfiguration
public class MybatisConfiguration {
    // 只添加扫描注解，其他配置留给全局配置文件
} 