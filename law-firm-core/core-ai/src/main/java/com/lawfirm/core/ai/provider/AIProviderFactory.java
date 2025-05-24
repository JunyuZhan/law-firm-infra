package com.lawfirm.core.ai.provider;

import com.lawfirm.core.ai.exception.AIException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI服务提供者工厂类
 * 负责管理和获取不同的AI服务提供者
 */
@Slf4j
@Component
public class AIProviderFactory {
    
    @Autowired
    private List<AIProvider> providers;
    
    private final Map<String, AIProvider> providerMap = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> providerConfigs = new HashMap<>();
    
    @PostConstruct
    public void init() {
        if (providers != null && !providers.isEmpty()) {
            for (AIProvider provider : providers) {
                providerMap.put(provider.getName(), provider);
                log.info("注册AI提供者: {}", provider.getName());
            }
        } else {
            log.warn("没有找到任何AI提供者实现");
        }
    }
    
    /**
     * 获取指定名称的提供者
     * 
     * @param providerName 提供者名称
     * @return AI服务提供者实例
     */
    public AIProvider getProvider(String providerName) {
        AIProvider provider = providerMap.get(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("未找到名为 " + providerName + " 的AI提供者");
        }
        return provider;
    }
    
    /**
     * 获取默认提供者
     * 
     * @return 默认AI服务提供者实例
     */
    public AIProvider getDefaultProvider() {
        // 优先使用OpenAI提供者
        if (providerMap.containsKey("openai")) {
            return providerMap.get("openai");
        }
        
        // 如果没有OpenAI，则使用第一个可用的提供者
        if (!providerMap.isEmpty()) {
            String firstKey = providerMap.keySet().iterator().next();
            return providerMap.get(firstKey);
        }
        
        throw new IllegalStateException("没有可用的AI提供者");
    }
    
    /**
     * 初始化指定提供者
     * 
     * @param providerName 提供者名称
     * @param config 配置信息
     * @return 是否初始化成功
     */
    public boolean initializeProvider(String providerName, Map<String, Object> config) {
        AIProvider provider = getProvider(providerName);
        boolean result = provider.initialize(config);
        if (result) {
            providerConfigs.put(providerName, config);
            log.info("成功初始化AI提供者: {}", providerName);
        } else {
            log.error("初始化AI提供者失败: {}", providerName);
        }
        return result;
    }
    
    /**
     * 获取所有注册的提供者名称
     * 
     * @return 提供者名称列表
     */
    public List<String> getAllProviderNames() {
        return List.copyOf(providerMap.keySet());
    }
    
    /**
     * 获取提供者配置
     * 
     * @param providerName 提供者名称
     * @return 配置信息
     */
    public Map<String, Object> getProviderConfig(String providerName) {
        return providerConfigs.getOrDefault(providerName, Map.of());
    }
    
    /**
     * 关闭所有提供者
     */
    public void shutdownAllProviders() {
        for (AIProvider provider : providerMap.values()) {
            try {
                provider.shutdown();
                log.info("已关闭AI提供者: {}", provider.getName());
            } catch (Exception e) {
                log.error("关闭AI提供者 {} 时发生错误", provider.getName(), e);
            }
        }
    }
} 