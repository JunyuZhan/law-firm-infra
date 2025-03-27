package com.lawfirm.document.service.strategy.search;

import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于Elasticsearch的文档搜索策略实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ElasticsearchSearchStrategy implements SearchStrategy {

    private final SearchService searchService;

    @Override
    public List<BaseDocument> searchByKeyword(String keyword, int page, int size) {
        log.debug("使用Elasticsearch搜索文档，关键词：{}, 页码：{}, 页大小：{}", keyword, page, size);
        
        // 构建搜索请求对象
        SearchRequestDTO request = new SearchRequestDTO();
        request.setKeyword(keyword);
        request.setIndexName("documents");
        request.setPageNum(page);
        request.setPageSize(size);
        request.addField("title").addField("keywords").addField("description").addField("content");
        
        // 调用搜索服务
        SearchVO searchResult = searchService.search(request);
        
        // 将搜索结果转换为文档对象
        return convertToDocuments(searchResult.getHits());
    }

    @Override
    public List<BaseDocument> searchByConditions(Map<String, Object> conditions, int page, int size) {
        log.debug("使用Elasticsearch搜索文档，条件：{}, 页码：{}, 页大小：{}", conditions, page, size);
        
        // 构建搜索请求对象
        SearchRequestDTO request = new SearchRequestDTO();
        request.setIndexName("documents");
        request.setPageNum(page);
        request.setPageSize(size);
        request.setFilters(conditions); // 设置查询条件
        
        // 调用搜索服务
        SearchVO searchResult = searchService.search(request);
        
        // 将搜索结果转换为文档对象
        return convertToDocuments(searchResult.getHits());
    }

    @Override
    public long countByKeyword(String keyword) {
        log.debug("使用Elasticsearch计算搜索结果数量，关键词：{}", keyword);
        
        // 构建搜索请求
        SearchRequestDTO request = new SearchRequestDTO();
        request.setKeyword(keyword);
        request.setIndexName("documents");
        request.setPageSize(1); // 只需要总数，不需要返回文档
        
        // 调用搜索服务获取总数
        SearchVO result = searchService.search(request);
        
        return result.getTotal();
    }

    @Override
    public long countByConditions(Map<String, Object> conditions) {
        log.debug("使用Elasticsearch计算搜索结果数量，条件：{}", conditions);
        
        // 构建搜索请求
        SearchRequestDTO request = new SearchRequestDTO();
        request.setIndexName("documents");
        request.setFilters(conditions);
        request.setPageSize(1); // 只需要总数，不需要返回文档
        
        // 调用搜索服务获取总数
        SearchVO result = searchService.search(request);
        
        return result.getTotal();
    }

    @Override
    public String getStrategyName() {
        return "elasticsearch";
    }
    
    /**
     * 将搜索结果转换为文档对象
     *
     * @param results 搜索结果
     * @return 文档对象列表
     */
    @SuppressWarnings("unchecked")
    private List<BaseDocument> convertToDocuments(List<SearchVO.Hit> hits) {
        if (hits == null) {
            return new ArrayList<>();
        }
        
        // 实际项目中应该有更复杂的转换逻辑
        // 这里简化处理，仅做示例
        return hits.stream()
                .map(hit -> mapToDocument(hit.getSource()))
                .toList();
    }
    
    /**
     * 将单个搜索结果映射为文档对象
     *
     * @param result 搜索结果
     * @return 文档对象
     */
    private BaseDocument mapToDocument(Map<String, Object> result) {
        BaseDocument document = new BaseDocument();
        
        // 设置文档属性
        if (result.containsKey("id")) {
            document.setId(Long.valueOf(result.get("id").toString()));
        }
        
        if (result.containsKey("title")) {
            document.setTitle((String) result.get("title"));
        }
        
        if (result.containsKey("doc_type")) {
            document.setDocType((String) result.get("doc_type"));
        }
        
        if (result.containsKey("description")) {
            document.setDescription((String) result.get("description"));
        }
        
        if (result.containsKey("keywords")) {
            document.setKeywords((String) result.get("keywords"));
        }
        
        if (result.containsKey("file_name")) {
            document.setFileName((String) result.get("file_name"));
        }
        
        if (result.containsKey("storage_path")) {
            document.setStoragePath((String) result.get("storage_path"));
        }
        
        return document;
    }
} 