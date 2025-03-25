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

import javax.sql.DataSource;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * 动态数据源配置
 * 只有在配置 spring.datasource.dynamic.enabled=true 时才生效
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource.dynamic", name = "enabled", havingValue = "true")
public class DynamicDataSourceConfig {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DefaultDataSourceCreator defaultDataSourceCreator() {
        return new DefaultDataSourceCreator();
    }

    @Bean
    @ConditionalOnMissingBean(name = "dynamicDataSourceProvider")
    public DynamicDataSourceProvider dynamicDataSourceProvider(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            DefaultDataSourceCreator dataSourceCreator) {
        return new AbstractDataSourceProvider(dataSourceCreator) {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSources = new HashMap<>(1);
                dataSources.put("master", masterDataSource);
                return dataSources;
            }
        };
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(Collections.singletonList(dynamicDataSourceProvider));
        dataSource.setPrimary("master");
        dataSource.setStrict(true);
        dataSource.setStrategy(LoadBalanceDynamicDataSourceStrategy.class);
        return dataSource;
    }
} 