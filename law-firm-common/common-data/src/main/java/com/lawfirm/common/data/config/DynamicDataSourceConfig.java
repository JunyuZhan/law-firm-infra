package com.lawfirm.common.data.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * 动态数据源配置
 * 只有在配置 spring.datasource.dynamic.enabled=true 时才生效
 * 并且必须在 law-firm.common.data.enabled=true 时才启用
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = {
    "spring.datasource.dynamic.enabled", 
    "law-firm.common.data.enabled"
}, havingValue = "true", matchIfMissing = false)
public class DynamicDataSourceConfig {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean(name = "defaultDataSourceCreator")
    @ConditionalOnMissingBean
    public DefaultDataSourceCreator defaultDataSourceCreator() {
        log.info("创建默认数据源创建器");
        return new DefaultDataSourceCreator();
    }

    @Bean(name = "dynamicDataSourceProvider")
    @ConditionalOnMissingBean(name = "dynamicDataSourceProvider")
    public DynamicDataSourceProvider dynamicDataSourceProvider(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            DefaultDataSourceCreator dataSourceCreator) {
        log.info("创建动态数据源提供者");
        return new AbstractDataSourceProvider(dataSourceCreator) {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSources = new HashMap<>(1);
                dataSources.put("master", masterDataSource);
                return dataSources;
            }
        };
    }

    @Bean(name = "dataSource")
    @Primary
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        log.info("创建动态路由数据源");
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary("master");
        dataSource.setStrict(true);
        dataSource.setStrategy(LoadBalanceDynamicDataSourceStrategy.class);
        
        // 直接获取并设置数据源，避免使用不兼容的API
        Map<String, DataSource> dataSources = dynamicDataSourceProvider.loadDataSources();
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            dataSource.addDataSource(entry.getKey(), entry.getValue());
        }
        
        return dataSource;
    }
} 