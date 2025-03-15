package com.lawfirm.document.manager.search;

import com.lawfirm.model.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchManager {
    private final SearchService searchService;
    private final DocumentSearchContext documentSearchContext;
    
    // 实现文档搜索相关功能
}
