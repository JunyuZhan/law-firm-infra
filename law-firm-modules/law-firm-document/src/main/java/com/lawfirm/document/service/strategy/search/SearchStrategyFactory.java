package com.lawfirm.document.service.strategy.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 搜索策略工厂
 * 简化实现，只使用数据库搜索策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SearchStrategyFactory {

    private final Map<String, SearchStrategy> strategyMap = new ConcurrentHashMap<>(1);
    private final DatabaseSearchStrategy databaseSearchStrategy;
    
    @PostConstruct
    public void init() {
        log.info("初始化搜索策略工厂");
        registerStrategy(databaseSearchStrategy);
    }
    
    /**
     * 注册搜索策略
     */
    public void registerStrategy(SearchStrategy strategy) {
        strategyMap.put(strategy.getStrategyName(), strategy);
        log.info("注册搜索策略: {}", strategy.getStrategyName());
    }
    
    /**
     * 获取搜索策略
     * 简化实现，始终返回数据库搜索策略
     */
    public SearchStrategy getStrategy() {
        return databaseSearchStrategy;
    }
} 