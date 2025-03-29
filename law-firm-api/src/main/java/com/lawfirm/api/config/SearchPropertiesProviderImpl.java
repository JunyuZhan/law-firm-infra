package com.lawfirm.api.config;

import com.lawfirm.core.search.config.SearchProperties;
import com.lawfirm.core.search.config.SearchPropertiesProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 搜索配置提供者实现类
 */
@Primary
@Component
public class SearchPropertiesProviderImpl implements SearchPropertiesProvider {
    
    @Override
    public SearchProperties getSearchProperties() {
        SearchProperties properties = new SearchProperties();
        // 设置默认搜索配置
        properties.setEnabled(true);
        properties.setEngine("lucene");
        properties.getLucene().setIndexDir("./lucene/index");
        return properties;
    }
}