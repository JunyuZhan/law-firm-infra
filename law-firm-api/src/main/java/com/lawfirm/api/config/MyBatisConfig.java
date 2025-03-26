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
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

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
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置别名包
        factoryBean.setTypeAliasesPackage("com.lawfirm.model");
        
        // 不设置XML映射文件位置，完全使用注解方式
        log.info("使用注解方式配置MyBatis映射，不加载XML映射文件");
        
        // 配置分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        factoryBean.setPlugins(interceptor);
        
        // MyBatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        factoryBean.setConfiguration(configuration);
        
        return factoryBean.getObject();
    }
} 