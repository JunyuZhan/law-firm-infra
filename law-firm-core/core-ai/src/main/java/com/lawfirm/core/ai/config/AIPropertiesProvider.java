package com.lawfirm.core.ai.config;

/**
 * AI配置提供者接口
 * 业务层实现此接口以提供AI服务配置
 */
public interface AIPropertiesProvider {
    
    /**
     * 获取AI服务配置
     * 
     * @return AI配置属性
     */
    AIConfig getAIConfig();
} 