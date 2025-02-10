package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文档版本服务接口
 */
public interface DocumentVersionService {
    
    /**
     * 创建新版本
     */
    DocumentVersion createVersion(Long documentId, MultipartFile file, String changeLog);
    
    /**
     * 获取版本详情
     */
    DocumentVersion getVersion(Long versionId);
    
    /**
     * 获取文档的所有版本
     */
    List<DocumentVersion> getDocumentVersions(Long documentId);
    
    /**
     * 获取文档的最新版本
     */
    DocumentVersion getLatestVersion(Long documentId);
    
    /**
     * 回滚到指定版本
     */
    DocumentVersion rollbackVersion(Long documentId, Integer version);
    
    /**
     * 删除版本
     */
    void deleteVersion(Long versionId);
    
    /**
     * 分页查询版本
     */
    Page<DocumentVersion> listVersions(Long documentId, Pageable pageable);
    
    /**
     * 比较两个版本
     */
    String compareVersions(Long documentId, Integer version1, Integer version2);
    
    /**
     * 获取版本文件
     */
    byte[] getVersionFile(Long versionId);
    
    /**
     * 获取版本文件URL
     */
    String getVersionFileUrl(Long versionId);
    
    /**
     * 清理历史版本
     */
    void cleanHistoryVersions(Long documentId, Integer keepVersions);
} 