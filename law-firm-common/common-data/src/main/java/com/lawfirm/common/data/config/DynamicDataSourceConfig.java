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

/**
 * 动态数据源配置
 * 只有在配置 spring.datasource.dynamic.enabled=true 时才生效
 * 并且必须在 law-firm.common.data.enabled=true 时才启用
 */
@Slf4j
@Configuration("commonDynamicDataSourceConfig")
@ConditionalOnProperty(value = {
    "spring.datasource.dynamic.enabled", 
    "law-firm.common.data.enabled"
}, havingValue = "true", matchIfMissing = false)
public class DynamicDataSourceConfig {

    @Autowired
    private DynamicDataSourceProperties properties;

    /**
     * 默认数据源创建器
     */
    @Bean(name = "commonDefaultDataSourceCreator")
    @ConditionalOnMissingBean(name = "commonDefaultDataSourceCreator")
    public DefaultDataSourceCreator commonDefaultDataSourceCreator() {
        log.info("创建默认数据源创建器");
        return new DefaultDataSourceCreator();
    }

    /**
     * 动态数据源提供者
     * 整合主数据源和其他数据源
     */
    @Bean(name = "commonDynamicDataSourceProvider")
    @ConditionalOnMissingBean(name = "commonDynamicDataSourceProvider")
    public DynamicDataSourceProvider commonDynamicDataSourceProvider(
            @Qualifier("dataSource") DataSource masterDataSource,
            @Qualifier("commonDefaultDataSourceCreator") DefaultDataSourceCreator dataSourceCreator) {
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

    /**
     * 提供动态路由数据源
     * 用于支持多数据源切换
     */
    @Bean(name = "commonDynamicRoutingDataSource")
    @ConditionalOnMissingBean(name = "commonDynamicRoutingDataSource")
    public DataSource commonDynamicRoutingDataSource(
            @Qualifier("commonDynamicDataSourceProvider") DynamicDataSourceProvider dynamicDataSourceProvider) {
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