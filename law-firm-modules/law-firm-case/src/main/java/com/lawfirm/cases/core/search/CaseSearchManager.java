package com.lawfirm.cases.core.search;

import org.springframework.stereotype.Component;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.service.IndexService;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.vo.SearchVO;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 案件搜索管理器
 * <p>
 * 负责与core-search模块集成，实现案件相关的搜索功能。
 * 包括全文检索、高级筛选、条件查询和聚合分析等。
 * </p>
 *
 * @author JunyuZhan
 */
@Slf4j
@Component
public class CaseSearchManager {
    
    private final SearchService searchService;
    private final IndexService indexService;
    
    private static final String CASE_INDEX = "cases";
    private static final String CASE_DOCUMENT_INDEX = "case_documents";
    
    /**
     * 构造函数，使用@Qualifier显式指定要注入的服务实现
     */
    public CaseSearchManager(
            @Qualifier("coreSearchServiceImpl") SearchService searchService,
            @Qualifier("coreIndexServiceImpl") IndexService indexService) {
        this.searchService = searchService;
        this.indexService = indexService;
    }
    
    /**
     * 索引案件数据
     *
     * @param caseId 案件ID
     * @param caseData 案件数据
     */
    public void indexCaseData(Long caseId, Map<String, Object> caseData) {
        log.info("索引案件数据, 案件ID: {}", caseId);
        
        try {
            caseData.put("id", caseId.toString());
            searchService.indexDoc(CASE_INDEX, caseId.toString(), caseData);
            
            log.info("案件数据索引成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件数据索引失败", e);
            throw new RuntimeException("索引案件数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 索引案件文档
     *
     * @param documentId 文档ID
     * @param caseId 案件ID
     * @param documentData 文档数据
     */
    public void indexCaseDocument(String documentId, Long caseId, Map<String, Object> documentData) {
        log.info("索引案件文档, 文档ID: {}, 案件ID: {}", documentId, caseId);
        
        try {
            documentData.put("id", documentId);
            documentData.put("case_id", caseId.toString());
            searchService.indexDoc(CASE_DOCUMENT_INDEX, documentId, documentData);
            
            log.info("案件文档索引成功, 文档ID: {}", documentId);
        } catch (Exception e) {
            log.error("案件文档索引失败", e);
            throw new RuntimeException("索引案件文档失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新案件索引
     *
     * @param caseId 案件ID
     * @param updateData 更新数据
     */
    public void updateCaseIndex(Long caseId, Map<String, Object> updateData) {
        log.info("更新案件索引, 案件ID: {}", caseId);
        
        try {
            searchService.updateDoc(CASE_INDEX, caseId.toString(), updateData);
            
            log.info("案件索引更新成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件索引更新失败", e);
            throw new RuntimeException("更新案件索引失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除案件索引
     *
     * @param caseId 案件ID
     */
    public void deleteCaseIndex(Long caseId) {
        log.info("删除案件索引, 案件ID: {}", caseId);
        
        try {
            searchService.deleteDoc(CASE_INDEX, caseId.toString());
            
            log.info("案件索引删除成功, 案件ID: {}", caseId);
        } catch (Exception e) {
            log.error("案件索引删除失败", e);
            throw new RuntimeException("删除案件索引失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索案件
     *
     * @param keywords 关键词
     * @param filters 过滤条件
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    public Map<String, Object> searchCases(String keywords, Map<String, Object> filters, int page, int size) {
        log.info("搜索案件, 关键词: {}, 过滤条件: {}, 页码: {}, 每页大小: {}", keywords, filters, page, size);
        
        try {
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(CASE_INDEX);
            request.setKeyword(keywords);
            request.setFilters(filters);
            request.setPageNum(page);
            request.setPageSize(size);
            SearchVO vo = searchService.search(request);
            return Map.of(
                "total", vo.getTotal(),
                "data", vo.getHits()
            );
        } catch (Exception e) {
            log.error("案件搜索失败", e);
            throw new RuntimeException("搜索案件失败: " + e.getMessage());
        }
    }
    
    /**
     * 搜索案件文档
     *
     * @param caseId 案件ID
     * @param keywords 关键词
     * @param filters 过滤条件
     * @param page 页码
     * @param size 每页大小
     * @return 搜索结果
     */
    public Map<String, Object> searchCaseDocuments(Long caseId, String keywords, Map<String, Object> filters, int page, int size) {
        log.info("搜索案件文档, 案件ID: {}, 关键词: {}, 页码: {}, 每页大小: {}", caseId, keywords, page, size);
        
        try {
            if (filters == null) {
                filters = new java.util.HashMap<>();
            }
            filters.put("case_id", caseId.toString());
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(CASE_DOCUMENT_INDEX);
            request.setKeyword(keywords);
            request.setFilters(filters);
            request.setPageNum(page);
            request.setPageSize(size);
            SearchVO vo = searchService.search(request);
            return Map.of(
                "total", vo.getTotal(),
                "data", vo.getHits()
            );
        } catch (Exception e) {
            log.error("案件文档搜索失败", e);
            throw new RuntimeException("搜索案件文档失败: " + e.getMessage());
        }
    }
    
    /**
     * 高级搜索案件
     *
     * @param queryParams 查询参数
     * @return 搜索结果
     */
    public Map<String, Object> advancedSearchCases(Map<String, Object> queryParams) {
        log.info("高级搜索案件, 查询参数: {}", queryParams);
        
        try {
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(CASE_INDEX);
            if (queryParams != null) {
                if (queryParams.containsKey("keyword")) {
                    request.setKeyword((String) queryParams.get("keyword"));
                }
                if (queryParams.containsKey("filters")) {
                    Object filtersObj = queryParams.get("filters");
                    if (filtersObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> filtersMap = (Map<String, Object>) filtersObj;
                        request.setFilters(new java.util.HashMap<>(filtersMap));
                    }
                }
                if (queryParams.containsKey("aggregations")) {
                    Object aggrObj = queryParams.get("aggregations");
                    if (aggrObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> aggrMap = (Map<String, Object>) aggrObj;
                        request.setAggregations(new java.util.HashMap<>(aggrMap));
                    }
                }
                if (queryParams.containsKey("pageNum")) {
                    request.setPageNum((Integer) queryParams.get("pageNum"));
                }
                if (queryParams.containsKey("pageSize")) {
                    request.setPageSize((Integer) queryParams.get("pageSize"));
                }
            }
            SearchVO vo = searchService.search(request);
            return Map.of(
                "total", vo.getTotal(),
                "data", vo.getHits(),
                "aggregations", vo.getAggregations()
            );
        } catch (Exception e) {
            log.error("案件高级搜索失败", e);
            throw new RuntimeException("高级搜索案件失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取案件统计聚合
     *
     * @param aggregationParams 聚合参数
     * @return 聚合结果
     */
    public Map<String, Object> getCaseAggregations(Map<String, Object> aggregationParams) {
        log.info("获取案件统计聚合, 聚合参数: {}", aggregationParams);
        
        try {
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(CASE_INDEX);
            request.setAggregations(aggregationParams);
            SearchVO vo = searchService.search(request);
            return vo.getAggregations() != null ? vo.getAggregations() : Map.of();
        } catch (Exception e) {
            log.error("案件统计聚合失败", e);
            throw new RuntimeException("获取案件统计聚合失败: " + e.getMessage());
        }
    }
} 