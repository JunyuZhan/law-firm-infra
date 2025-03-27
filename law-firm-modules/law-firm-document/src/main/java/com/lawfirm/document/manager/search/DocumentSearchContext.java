package com.lawfirm.document.manager.search;

import com.lawfirm.model.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 文档搜索上下文，提供文档搜索相关的配置和上下文信息
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = false)
public class DocumentSearchContext {
    
    private final SearchService searchService;
    
    /**
     * 构造函数
     * 
     * @param searchService 搜索服务（可能为null）
     */
    public DocumentSearchContext(SearchService searchService) {
        this.searchService = searchService;
        log.info("文档搜索上下文初始化完成，搜索服务状态: {}", searchService != null ? "可用" : "不可用");
    }
    
    /**
     * 获取文档索引名称
     * 
     * @return 文档索引名称
     */
    public String getDocumentIndexName() {
        // 可以从配置中获取，也可以使用固定值
        return "lawfirm_documents";
    }
    
    /**
     * 获取模板索引名称
     * 
     * @return 模板索引名称
     */
    public String getTemplateIndexName() {
        // 可以从配置中获取，也可以使用固定值
        return "lawfirm_templates";
    }
    
    /**
     * 获取搜索服务
     * 
     * @return 搜索服务
     */
    public SearchService getSearchService() {
        return searchService;
    }
}
