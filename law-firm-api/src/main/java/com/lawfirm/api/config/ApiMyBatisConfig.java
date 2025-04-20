package com.lawfirm.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * API层MyBatis-Plus专用配置
 * <p>
 * 解决MyBatis和MyBatis-Plus冲突问题
 * </p>
 */
@Configuration
public class ApiMyBatisConfig {

    /**
     * 创建SqlSessionFactory
     * <p>
     * 使用MybatisSqlSessionFactoryBean替代SqlSessionFactoryBean
     * 确保仅使用MyBatis-Plus的配置，避免与原生MyBatis冲突
     * </p>
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置Mapper XML位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:/mapper/**/*.xml");
        factoryBean.setMapperLocations(resources);
        
        // 设置实体类别名包
        factoryBean.setTypeAliasesPackage("com.lawfirm.model");
        
        // 配置MyBatis-Plus全局配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setAggressiveLazyLoading(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultStatementTimeout(30);
        configuration.setCallSettersOnNulls(true);
        
        factoryBean.setConfiguration(configuration);
        
        return factoryBean.getObject();
    }
} 