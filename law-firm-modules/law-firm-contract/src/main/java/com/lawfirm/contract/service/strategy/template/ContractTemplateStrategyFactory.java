package com.lawfirm.contract.service.strategy.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同模板策略工厂
 * 用于根据类型获取相应的模板策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContractTemplateStrategyFactory {

    private final List<ContractTemplateStrategy> templateStrategies;
    
    private final Map<String, ContractTemplateStrategy> strategyMap = new HashMap<>();
    
    /**
     * 初始化策略映射
     */
    @PostConstruct
    public void init() {
        for (ContractTemplateStrategy strategy : templateStrategies) {
            strategyMap.put(strategy.getType(), strategy);
            log.info("注册合同模板策略: {}", strategy.getType());
        }
    }
    
    /**
     * 获取合同模板策略
     * @param strategyType 策略类型
     * @return 对应的策略实现
     */
    public ContractTemplateStrategy getStrategy(String strategyType) {
        ContractTemplateStrategy strategy = strategyMap.get(strategyType);
        if (strategy == null) {
            log.warn("未找到合同模板策略: {}, 使用标准策略替代", strategyType);
            // 默认使用标准策略
            strategy = strategyMap.get("STANDARD");
        }
        return strategy;
    }
} 