package com.lawfirm.core.search.config;

/**
 * 搜索配置提供者接口
 * 业务层实现此接口以提供搜索配置
 */
public interface SearchPropertiesProvider {
    
    /**
     * 获取搜索配置
     * 
     * @return 搜索配置属性
     */
    SearchProperties getSearchProperties();
} 