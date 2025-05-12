package com.lawfirm.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;

/**
 * 系统模块MyBatis配置类
 * 解决mapper扫描问题，确保BaseMapper方法能正确映射
 */
@Configuration("systemMybatisConfig")
@MapperScan(basePackages = "com.lawfirm.model.system.mapper")
public class SystemMybatisConfig {

    /**
     * 创建SQL Session工厂
     */
    @Primary
    @Bean("systemSqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        
        // 设置MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        factory.setConfiguration(configuration);
        
        // 设置类型别名包
        factory.setTypeAliasesPackage("com.lawfirm.model.system.entity");
        
        // 全局配置
        com.baomidou.mybatisplus.core.config.GlobalConfig globalConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig();
        
        // 数据库字段配置
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig();
        dbConfig.setLogicDeleteField("deleted");
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        globalConfig.setDbConfig(dbConfig);
        
        factory.setGlobalConfig(globalConfig);
        
        return factory;
    }
} 