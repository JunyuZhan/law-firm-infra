package com.lawfirm.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * MyBatis配置类
 * 此配置不使用XML编码，全部使用注解方式
 */
@Configuration
@MapperScan(basePackages = {"com.lawfirm.model.**.mapper"})
public class MyBatisConfig {

    private static final Logger log = LoggerFactory.getLogger(MyBatisConfig.class);

    /**
     * 创建SqlSessionFactory
     * 使用common-data模块提供的MybatisPlusInterceptor
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, 
                                              @Qualifier("commonMybatisPlusInterceptor") MybatisPlusInterceptor interceptor) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 不使用自动扫描别名方式，避免别名冲突
        // factoryBean.setTypeAliasesPackage("com.lawfirm.model.*.entity");
        log.info("禁用自动别名扫描，避免别名冲突问题");
        
        // 不设置XML映射文件位置，完全使用注解方式
        log.info("使用注解方式配置MyBatis映射，不加载XML映射文件");
        
        // 使用common-data模块提供的拦截器，避免重复创建
        log.info("使用common-data模块提供的MybatisPlusInterceptor");
        factoryBean.setPlugins(interceptor);
        
        // MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        
        // 使用configuration手动注册需要的类型处理器
        // configuration.getTypeHandlerRegistry().register(YourTypeHandler.class);
        
        factoryBean.setConfiguration(configuration);
        
        return factoryBean.getObject();
    }
} 