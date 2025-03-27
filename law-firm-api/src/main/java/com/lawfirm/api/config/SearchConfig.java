package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.lawfirm.core.search.config.SearchAutoConfiguration;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务配置类
 * 导入core-search模块中的配置
 */
@Configuration
@Import(SearchAutoConfiguration.class)
public class SearchConfig {
    
    /**
     * 提供一个默认的SearchService实现
     * 当search.enabled=true但找不到其他实现时使用
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(SearchService.class)
    public SearchService defaultSearchService() {
        // 返回null，由于使用了@ConditionalOnMissingBean，
        // 这个Bean只会在找不到其他SearchService实现时才会被使用
        // 同时我们已经禁用了search.enabled=false，相关依赖组件不会加载
        return null;
    }

    /**
     * 解决searchProperties Bean冲突
     * 当elasticsearch.enabled=false时，提供一个空的Bean来避免搜索模块的错误
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "elasticsearch.enabled", havingValue = "false")
    public com.lawfirm.core.search.config.SearchProperties coreSearchProperties() {
        return new com.lawfirm.core.search.config.SearchProperties();
    }
} 