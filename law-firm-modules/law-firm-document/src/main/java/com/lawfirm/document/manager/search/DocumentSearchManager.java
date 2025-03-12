package com.lawfirm.document.manager.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.vo.SearchVO;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档搜索管理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentSearchManager {

    private final SearchService searchService;
    private final AiService aiService;
    private final DocumentIndexManager indexManager;
    private static final String INDEX_NAME = "documents";

    /**
     * 全文检索
     *
     * @param keyword 关键词
     * @return 文档列表
     */
    public Page<DocumentVO> search(String keyword) {
        return searchDocument(keyword, Collections.emptyMap(), 1, 20);
    }

    /**
     * 分页搜索文档
     *
     * @param keyword  关键词
     * @param filters  过滤条件
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 搜索结果
     */
    public Page<DocumentVO> searchDocument(String keyword, Map<String, Object> filters, int pageNum, int pageSize) {
        // 1. 构建搜索请求
        SearchRequestDTO request = new SearchRequestDTO();
        request.setIndexName(INDEX_NAME);
        request.setKeyword(keyword);
        request.setFilters(filters);
        request.setPageNum(pageNum);
        request.setPageSize(pageSize);

        // 2. 执行搜索
        SearchVO result = searchService.search(request);

        // 3. 转换结果
        List<DocumentVO> documents = result.getHits().stream()
            .map(hit -> {
                DocumentVO doc = new DocumentVO();
                doc.setId(Long.valueOf(hit.getId()));
                doc.setTitle((String) hit.getSource().get("title"));
                doc.setDocType((String) hit.getSource().get("docType"));
                doc.setFileName((String) hit.getSource().get("fileName"));
                doc.setFileSize((Long) hit.getSource().get("fileSize"));
                doc.setFileType((String) hit.getSource().get("fileType"));
                doc.setStoragePath((String) hit.getSource().get("storagePath"));
                doc.setStorageType((String) hit.getSource().get("storageType"));
                doc.setDocStatus((String) hit.getSource().get("docStatus"));
                doc.setDocumentVersion((String) hit.getSource().get("documentVersion"));
                doc.setKeywords((String) hit.getSource().get("keywords"));
                doc.setDescription((String) hit.getSource().get("description"));
                doc.setAccessLevel((String) hit.getSource().get("accessLevel"));
                doc.setIsEncrypted((Boolean) hit.getSource().get("isEncrypted"));
                doc.setDownloadCount((Long) hit.getSource().get("downloadCount"));
                doc.setViewCount((Long) hit.getSource().get("viewCount"));
                doc.setCreateTime(((String) hit.getSource().get("createTime")) != null ? 
                    LocalDateTime.parse((String) hit.getSource().get("createTime")) : null);
                doc.setUpdateTime(((String) hit.getSource().get("updateTime")) != null ? 
                    LocalDateTime.parse((String) hit.getSource().get("updateTime")) : null);
                doc.setCreateBy(Long.valueOf((String) hit.getSource().get("createBy")));
                doc.setUpdateBy(Long.valueOf((String) hit.getSource().get("updateBy")));
                return doc;
            })
            .collect(Collectors.toList());

        // 4. 构建分页结果
        Page<DocumentVO> page = new Page<>(pageNum, pageSize);
        page.setTotal(result.getTotal());
        page.setRecords(documents);
        return page;
    }

    /**
     * 相关文档推荐
     *
     * @param documentId 文档ID
     * @param maxResults 最大结果数
     * @return 相关文档列表
     */
    public List<DocumentVO> recommend(Long documentId, int maxResults) {
        // 1. 获取源文档
        Map<String, Object> sourceDoc = searchService.getDoc(INDEX_NAME, documentId.toString());
        if (sourceDoc == null) {
            return Collections.emptyList();
        }

        // 2. 构建相似度查询
        Map<String, Object> query = new HashMap<>();
        query.put("title", sourceDoc.get("title"));
        query.put("keywords", sourceDoc.get("keywords"));
        query.put("description", sourceDoc.get("description"));

        // 3. 执行搜索
        SearchRequestDTO request = new SearchRequestDTO();
        request.setIndexName(INDEX_NAME);
        request.setFilters(query);
        request.setPageNum(0);
        request.setPageSize(maxResults);

        SearchVO result = searchService.search(request);

        // 4. 转换结果
        return result.getHits().stream()
            .filter(hit -> !hit.getId().equals(documentId.toString()))
            .map(hit -> {
                DocumentVO doc = new DocumentVO();
                doc.setId(Long.valueOf(hit.getId()));
                doc.setTitle((String) hit.getSource().get("title"));
                doc.setDocType((String) hit.getSource().get("docType"));
                doc.setFileName((String) hit.getSource().get("fileName"));
                doc.setDescription((String) hit.getSource().get("description"));
                doc.setKeywords((String) hit.getSource().get("keywords"));
                return doc;
            })
            .collect(Collectors.toList());
    }

    /**
     * 获取搜索建议
     *
     * @param prefix 前缀
     * @return 建议列表
     */
    public List<String> suggest(String prefix) {
        return searchService.suggest(INDEX_NAME, "title", prefix);
    }

    /**
     * 生成文档摘要
     *
     * @param documentId 文档ID
     * @return 摘要内容
     */
    public String generateSummary(Long documentId) {
        // 1. 获取文档内容
        Map<String, Object> document = searchService.getDoc("documents", documentId.toString());
        if (document == null) {
            return null;
        }

        // 2. 使用AI服务生成摘要
        return aiService.generateSummary((String) document.get("content"));
    }

    /**
     * 提取关键信息
     *
     * @param documentId 文档ID
     * @return 关键信息映射
     */
    public Map<String, Object> extractKeyInfo(Long documentId) {
        // 1. 获取文档内容
        Map<String, Object> document = searchService.getDoc("documents", documentId.toString());
        if (document == null) {
            return Map.of();
        }

        // 2. 使用AI服务提取关键信息
        return aiService.extractKeyInfo((String) document.get("content"));
    }
}
