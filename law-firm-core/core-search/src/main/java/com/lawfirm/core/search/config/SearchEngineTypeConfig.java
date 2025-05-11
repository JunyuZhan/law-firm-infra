package com.lawfirm.core.search.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 搜索引擎类型配置
 * <p>
 * 用于配置并初始化搜索引擎类型
 * </p>
 */
@Slf4j
@Configuration
public class SearchEngineTypeConfig {

    private final Environment environment;
    
    /**
     * 从配置文件中读取搜索引擎类型
     * 属性名已从type更新为searchEngineType
     */
    @Value("${law-firm.search.searchEngineType:database}")
    private String searchType;
    
    public SearchEngineTypeConfig(Environment environment) {
        this.environment = environment;
    }
    
    @PostConstruct
    public void init() {
        log.info("设置搜索引擎类型系统属性: {}", searchType);
        
        // 设置一个系统属性，便于其他模块获取当前搜索引擎类型
        System.setProperty("law-firm.search.current.type", searchType);
    }
} 