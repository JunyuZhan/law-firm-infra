package com.lawfirm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * API层启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lawfirm"},
    excludeFilters = {
        // 排除所有模型层的服务实现
        @ComponentScan.Filter(type = FilterType.REGEX, 
                pattern = "com\\.lawfirm\\.model\\..*\\.service\\.impl\\..*"),
        // 排除非API层的WebConfig
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.common.web.config.WebConfig.class}),
        // 排除非API层的XssFilter
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.common.web.filter.XssFilter.class}),
        // 排除非API层相关的CacheConfig
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.common.data.config.CacheConfig.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.core.workflow.config.CacheConfig.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.common.cache.config.CacheConfig.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.client.config.CacheConfig.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.contract.config.CacheConfig.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.finance.config.CacheConfig.class}),
        // 排除document模块的存储策略，优先使用core模块的实现
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.document.service.strategy.storage.DocumentLocalStorageStrategy.class}),
        // 排除personnel模块的消息配置，优先使用core模块的实现
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
                classes = {com.lawfirm.personnel.config.PersonnelMessageConfig.class})
    }
)
public class LawFirmApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
} 