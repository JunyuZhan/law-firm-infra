package com.lawfirm.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MyBatis配置类
 * 使用统一的MapperScan配置，避免重复扫描导致的冲突
 */
@Configuration
@MapperScan(
    basePackages = {
        "com.lawfirm.model.**.mapper"
    },
    sqlSessionFactoryRef = "sqlSessionFactory"
)
public class MyBatisConfig {

    /**
     * 创建SqlSessionFactory
     * 避免了XML解析错误和类型冲突问题
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置MapperLocations，排除损坏的XML文件
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList = new ArrayList<>();
        
        try {
            // 添加Mapper XML文件
            Resource[] customResources = resolver.getResources("classpath*:/mapper/**/*.xml");
            resourceList.addAll(Arrays.asList(customResources));
            
            // 过滤损坏的XML文件
            resourceList.removeIf(resource -> 
                resource.getFilename() != null && 
                resource.getFilename().endsWith("Mapper.xml") &&
                (resource.getFilename().contains("LoginHistory") || 
                 resource.getFilename().contains("Document"))
            );
                
        } catch (IOException e) {
            // 记录异常但不中断启动
            System.err.println("Error loading mapper files: " + e.getMessage());
        }
        
        factoryBean.setMapperLocations(resourceList.toArray(new Resource[0]));
        
        // 恢复使用包扫描，但更精确指定包和排除冲突类
        // 添加model和core包下的实体类路径
        factoryBean.setTypeAliasesPackage(
            "com.lawfirm.model.auth.entity;" +
            "com.lawfirm.model.client.entity;" +
            "com.lawfirm.model.cases.entity;" +
            "com.lawfirm.model.contract.entity;" +
            "com.lawfirm.model.document.entity;" +
            "com.lawfirm.model.finance.entity;" +
            "com.lawfirm.model.log.entity;" +
            "com.lawfirm.model.organization.entity;" +
            "com.lawfirm.model.personnel.entity;" +
            "com.lawfirm.model.search.entity;" +
            "com.lawfirm.model.storage.entity;" +
            "com.lawfirm.model.system.entity;" +
            "com.lawfirm.model.workflow.entity;" +
            "com.lawfirm.core.workflow.entity;" +
            "com.lawfirm.core.**.entity"
        );
        
        // 配置类型处理器
        factoryBean.setTypeHandlersPackage("com.lawfirm.common.core.handler");
        
        // 配置分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        factoryBean.setPlugins(interceptor);
        
        // 全局配置
        com.baomidou.mybatisplus.core.config.GlobalConfig globalConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig();
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig();
        dbConfig.setLogicDeleteField("deleted");
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        globalConfig.setDbConfig(dbConfig);
        factoryBean.setGlobalConfig(globalConfig);
        
        // 设置配置属性，避免别名冲突
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setAutoMappingBehavior(org.apache.ibatis.session.AutoMappingBehavior.PARTIAL);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setCacheEnabled(false);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultEnumTypeHandler(org.apache.ibatis.type.EnumOrdinalTypeHandler.class);
        factoryBean.setConfiguration(configuration);
        
        return factoryBean.getObject();
    }
} 