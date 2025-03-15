package com.lawfirm.document.manager.search;

import com.lawfirm.model.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 文档搜索上下文，提供文档搜索相关的配置和上下文信息
 */
@Component
@RequiredArgsConstructor
public class DocumentSearchContext {
    
    private final SearchService searchService;
    
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
