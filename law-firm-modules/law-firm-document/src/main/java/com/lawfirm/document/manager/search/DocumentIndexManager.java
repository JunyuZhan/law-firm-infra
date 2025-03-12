package com.lawfirm.document.manager.search;

import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.document.entity.base.BaseDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 文档索引管理器
 * 负责处理文档索引的创建、更新和删除
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentIndexManager {

    private final SearchService searchService;
    private static final String INDEX_NAME = "documents";

    /**
     * 创建文档索引
     *
     * @param document 文档实体
     */
    public void createIndex(BaseDocument document) {
        try {
            // 1. 构建索引文档
            Map<String, Object> indexDoc = buildIndexDoc(document);
            
            // 2. 创建索引
            searchService.indexDoc(INDEX_NAME, document.getId().toString(), indexDoc);
            
            log.info("创建文档索引成功, documentId: {}", document.getId());
        } catch (Exception e) {
            log.error("创建文档索引失败, documentId: {}", document.getId(), e);
            throw new RuntimeException("创建文档索引失败", e);
        }
    }

    /**
     * 更新文档索引
     *
     * @param document 文档实体
     */
    public void updateIndex(BaseDocument document) {
        try {
            // 1. 构建索引文档
            Map<String, Object> indexDoc = buildIndexDoc(document);
            
            // 2. 更新索引
            searchService.updateDoc(INDEX_NAME, document.getId().toString(), indexDoc);
            
            log.info("更新文档索引成功, documentId: {}", document.getId());
        } catch (Exception e) {
            log.error("更新文档索引失败, documentId: {}", document.getId(), e);
            throw new RuntimeException("更新文档索引失败", e);
        }
    }

    /**
     * 删除文档索引
     *
     * @param documentId 文档ID
     */
    public void deleteIndex(Long documentId) {
        try {
            // 删除索引
            searchService.deleteDoc(INDEX_NAME, documentId.toString());
            
            log.info("删除文档索引成功, documentId: {}", documentId);
        } catch (Exception e) {
            log.error("删除文档索引失败, documentId: {}", documentId, e);
            throw new RuntimeException("删除文档索引失败", e);
        }
    }

    /**
     * 构建索引文档
     */
    private Map<String, Object> buildIndexDoc(BaseDocument document) {
        Map<String, Object> indexDoc = new HashMap<>();
        
        // 1. 基本信息
        indexDoc.put("id", document.getId());
        indexDoc.put("title", document.getTitle());
        indexDoc.put("docType", document.getDocType());
        indexDoc.put("fileName", document.getFileName());
        indexDoc.put("fileSize", document.getFileSize());
        indexDoc.put("fileType", document.getFileType());
        indexDoc.put("storagePath", document.getStoragePath());
        indexDoc.put("storageType", document.getStorageType());
        
        // 2. 业务信息
        indexDoc.put("docStatus", document.getDocStatus());
        indexDoc.put("documentVersion", document.getDocumentVersion());
        indexDoc.put("keywords", document.getKeywords());
        indexDoc.put("description", document.getDescription());
        indexDoc.put("accessLevel", document.getAccessLevel());
        indexDoc.put("isEncrypted", document.getIsEncrypted());
        
        // 3. 统计信息
        indexDoc.put("downloadCount", document.getDownloadCount());
        indexDoc.put("viewCount", document.getViewCount());
        
        // 4. 时间信息
        indexDoc.put("createTime", document.getCreateTime());
        indexDoc.put("updateTime", document.getUpdateTime());
        indexDoc.put("createBy", document.getCreateBy());
        indexDoc.put("updateBy", document.getUpdateBy());
        
        return indexDoc;
    }
} 