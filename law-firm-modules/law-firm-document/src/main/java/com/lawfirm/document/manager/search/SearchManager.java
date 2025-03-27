package com.lawfirm.document.manager.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 文档搜索管理器
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = false)
public class SearchManager {

    private final DocumentSearchContext searchContext;
    
    public SearchManager(DocumentSearchContext searchContext) {
        this.searchContext = searchContext;
        log.info("文档搜索管理器初始化完成");
    }
}
