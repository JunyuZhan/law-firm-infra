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
        // 使用新的构造方法创建动态路由数据源
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(
                Collections.singletonList(dynamicDataSourceProvider));
        
        // 设置默认数据源名称
        dataSource.setPrimary(properties.getPrimary());
        
        // 新版本不需要显式调用init，构造函数中会自动初始化
        
        return dataSource;
    }
} 