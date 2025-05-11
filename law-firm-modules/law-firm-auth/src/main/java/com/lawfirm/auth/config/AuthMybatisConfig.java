package com.lawfirm.auth.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Auth模块MyBatis配置类
 * 专门为认证模块配置SqlSessionFactory和SqlSessionTemplate
 */
@Configuration("authMybatisConfig")
@MapperScan(basePackages = "com.lawfirm.model.auth.mapper", sqlSessionFactoryRef = "authSqlSessionFactory")
public class AuthMybatisConfig {

    /**
     * 创建Auth模块的SqlSessionFactory
     */
    @Bean("authSqlSessionFactory")
    public SqlSessionFactory authSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置MyBatis配置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/auth/**/*.xml"));
        factoryBean.setTypeAliasesPackage("com.lawfirm.model.auth.entity");
        
        return factoryBean.getObject();
    }
    
    /**
     * 创建Auth模块的SqlSessionTemplate
     */
    @Bean("authSqlSessionTemplate")
    public SqlSessionTemplate authSqlSessionTemplate(@Qualifier("authSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
} 