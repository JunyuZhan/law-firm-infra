package com.lawfirm.common.data.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * MyBatis会话工厂配置
 * <p>
 * 负责创建和配置SqlSessionFactory
 * 只有在law-firm.common.data.enabled=true时才启用
 * </p>
 * <p>
 * 注意：此类不再负责Mapper接口扫描，遵循架构分层原则
 * Mapper扫描职责已移至API层的ApiMapperScanConfig类
 * 基础设施层不应直接依赖领域模型层
 * </p>
 */
@Configuration
@ConditionalOnProperty(name = "law-firm.common.data.enabled", havingValue = "true", matchIfMissing = true)
public class SessionFactoryConfig {

    private static final Logger log = LoggerFactory.getLogger(SessionFactoryConfig.class);

    /**
     * 创建SqlSessionFactory
     * 使用MybatisPlusConfig提供的MybatisPlusInterceptor
     * 只有当DataSource存在时才创建
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    @ConditionalOnBean(DataSource.class)
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, 
                                             @Qualifier("commonMybatisPlusInterceptor") MybatisPlusInterceptor interceptor) throws Exception {
        log.info("初始化MyBatis和MyBatis-Plus配置");
        
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 不使用自动扫描别名方式，避免别名冲突
        log.info("禁用自动别名扫描，避免别名冲突问题");
        
        // 不设置XML映射文件位置，完全使用注解方式
        log.info("使用注解方式配置MyBatis映射，不加载XML映射文件");
        
        // 使用MybatisPlusConfig提供的拦截器
        log.info("使用common-data模块提供的MybatisPlusInterceptor");
        factoryBean.setPlugins(interceptor);
        
        // MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        
        factoryBean.setConfiguration(configuration);
        
        return factoryBean.getObject();
    }
} 