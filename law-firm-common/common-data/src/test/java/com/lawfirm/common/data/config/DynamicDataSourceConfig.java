package com.lawfirm.common.data.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源配置测试类
 */
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 默认数据源创建器
     */
    @Bean
    public DefaultDataSourceCreator dataSourceCreator() {
        return new DefaultDataSourceCreator();
    }

    /**
     * 创建动态数据源提供者
     */
    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource,
            DefaultDataSourceCreator dataSourceCreator) {
        
        return new DynamicDataSourceProvider() {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSourceMap = new HashMap<>(2);
                dataSourceMap.put("master", masterDataSource);
                dataSourceMap.put("slave", slaveDataSource);
                return dataSourceMap;
            }
        };
    }

    /**
     * 创建动态路由数据源
     */
    @Bean
    @Primary
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider,
                                DynamicDataSourceProperties properties) {
        // 使用无参构造函数创建动态路由数据源
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        
        // 设置默认数据源名称
        dataSource.setPrimary(properties.getPrimary());
        
        // 直接获取并设置数据源
        Map<String, DataSource> dataSources = dynamicDataSourceProvider.loadDataSources();
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            dataSource.addDataSource(entry.getKey(), entry.getValue());
        }
        
        return dataSource;
    }
} 