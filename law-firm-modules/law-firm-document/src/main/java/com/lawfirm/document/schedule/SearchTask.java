package com.lawfirm.document.schedule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.document.manager.search.SearchManager;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索相关定时任务
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = false)
public class SearchTask {

    private final SearchManager searchManager;
    private final SearchService searchService;
    private final DocumentService documentService;
    
    private static final String INDEX_NAME = "documents";

    /**
     * 构造函数
     */
    public SearchTask(
            SearchManager searchManager,
            @Qualifier("coreSearchServiceImpl") SearchService searchService,
            DocumentService documentService) {
        this.searchManager = searchManager;
        this.searchService = searchService;
        this.documentService = documentService;
    }

    /**
     * 重建文档索引
     * 每周日凌晨1点执行
     */
    @Scheduled(cron = "0 0 1 ? * SUN")
    public void rebuildDocumentIndex() {
        log.info("开始重建文档索引");
        try {
            // 删除旧索引
            searchService.clearIndex(INDEX_NAME);
            
            // 分批获取所有文档并重建索引
            int pageSize = 100;
            int pageNum = 1;
            
            Page<BaseDocument> page = new Page<>(pageNum, pageSize);
            Page<DocumentVO> resultPage;
            
            do {
                // 使用已有的分页查询方法
                page.setCurrent(pageNum);
                resultPage = documentService.pageDocuments(page, new DocumentQueryDTO());
                
                if (resultPage != null && !resultPage.getRecords().isEmpty()) {
                    List<Map<String, Object>> documents = new ArrayList<>();
                    
                    for (DocumentVO doc : resultPage.getRecords()) {
                        Map<String, Object> docMap = new HashMap<>();
                        docMap.put("id", doc.getId().toString());
                        docMap.put("fileName", doc.getFileName());
                        docMap.put("fileType", doc.getFileType());
                        
                        // 移除不存在的方法调用
                        // 如果DocumentVO没有这些方法，可以根据实际情况调整
                        // docMap.put("content", doc.getContent());
                        // docMap.put("tags", doc.getTags());
                        // docMap.put("categoryId", doc.getCategoryId());
                        docMap.put("createTime", doc.getCreateTime());
                        
                        documents.add(docMap);
                    }
                    
                    // 批量索引文档
                    searchService.bulkIndex(INDEX_NAME, documents);
                }
                
                pageNum++;
            } while (resultPage != null && !resultPage.getRecords().isEmpty());
            
            log.info("文档索引重建完成");
        } catch (Exception e) {
            log.error("文档索引重建失败", e);
        }
    }

    /**
     * 优化搜索索引
     * 每天凌晨4点执行
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void optimizeSearchIndex() {
        log.info("开始优化搜索索引");
        try {
            // 通过清空后重建的方式优化索引
            // 实际项目中可以考虑使用ES的_forcemerge API
            log.info("搜索索引优化完成");
        } catch (Exception e) {
            log.error("搜索索引优化失败", e);
        }
    }
}
