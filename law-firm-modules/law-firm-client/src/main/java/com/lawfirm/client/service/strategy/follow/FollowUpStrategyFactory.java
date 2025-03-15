package com.lawfirm.client.service.strategy.follow;

import com.lawfirm.model.client.entity.base.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 跟进策略工厂类
 * 用于根据客户情况选择合适的跟进策略
 */
@Component
@RequiredArgsConstructor
public class FollowUpStrategyFactory {
    
    /**
     * 注入所有策略实现
     */
    private final List<FollowUpStrategy> strategies;
    
    /**
     * 策略缓存，避免频繁查找
     */
    private final Map<String, FollowUpStrategy> strategyCache = new ConcurrentHashMap<>();
    
    /**
     * 根据类型获取策略
     * @param type 策略类型
     * @return 策略实现
     */
    public FollowUpStrategy getStrategy(String type) {
        // 从缓存中获取
        return strategyCache.computeIfAbsent(type, key -> 
            strategies.stream()
                .filter(strategy -> strategy.getType().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未找到类型为 " + key + " 的跟进策略"))
        );
    }
    
    /**
     * 根据客户信息选择适用的策略
     * @param client 客户信息
     * @return 适用的策略集合
     */
    public List<FollowUpStrategy> getApplicableStrategies(Client client) {
        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(client))
                .collect(Collectors.toList());
    }
    
    /**
     * 根据客户信息选择最优先的策略
     * @param client 客户信息
     * @return 最优先的策略
     */
    public FollowUpStrategy getPriorityStrategy(Client client) {
        List<FollowUpStrategy> applicableStrategies = getApplicableStrategies(client);
        
        if (applicableStrategies.isEmpty()) {
            // 如果没有适用的策略，使用常规跟进策略
            return getStrategy("regular");
        }
        
        // 简单的优先级逻辑：事件触发 > 常规跟进
        return applicableStrategies.stream()
                .filter(strategy -> "event".equals(strategy.getType()))
                .findFirst()
                .orElse(applicableStrategies.get(0));
    }
}
